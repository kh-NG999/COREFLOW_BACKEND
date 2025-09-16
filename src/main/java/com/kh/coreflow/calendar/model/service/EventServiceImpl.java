package com.kh.coreflow.calendar.model.service;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kh.coreflow.calendar.model.dao.CalendarDao;
import com.kh.coreflow.calendar.model.dao.EventDao;
import com.kh.coreflow.calendar.model.dto.EventDto;
import com.kh.coreflow.common.exception.ConflictException;
import com.kh.coreflow.conference.dao.ConferenceRoomDao;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {

    private final EventDao eventDao;
    private final CalendarDao calendarDao;
    private final ConferenceRoomDao conferenceRoomDao;

    // "yyyy-MM-dd HH:mm:ss" (공백형) 우선 지원
    private static final DateTimeFormatter FMT_SPACE =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    /** 프런트에서 오는 문자열을 견고하게 파싱 (공백/ISO/UTC 모두 수용) */
    private static LocalDateTime parseClientDateTime(String s) {
        if (s == null) throw new DateTimeParseException("null datetime", "null", 0);
        s = s.trim();

        // 1) "yyyy-MM-dd HH:mm:ss"
        try { return LocalDateTime.parse(s, FMT_SPACE); } catch (DateTimeParseException ignore) {}

        // 2) ISO_LOCAL_DATE_TIME (yyyy-MM-dd'T'HH:mm:ss)
        try { return LocalDateTime.parse(s, DateTimeFormatter.ISO_LOCAL_DATE_TIME); } catch (DateTimeParseException ignore) {}

        // 3) 날짜만 온 경우 → 자정으로 보정
        if (s.length() == 10) { // yyyy-MM-dd
            return LocalDateTime.parse(s + " 00:00:00", FMT_SPACE);
        }

        // 4) Offset/UTC 형태 수용 (예: 2025-08-31T00:00:00Z, +09:00 등)
        try { return OffsetDateTime.parse(s).toLocalDateTime(); } catch (Exception ignore) {}
        try { return Instant.parse(s).atZone(ZoneId.systemDefault()).toLocalDateTime(); } catch (Exception ignore) {}

        throw new DateTimeParseException("Unparseable datetime: " + s, s, 0);
    }

    @Override
    public List<EventDto.Res> getEvents(Long userNo, Long calendarId, String fromStr, String toStr) {
        LocalDateTime fromLdt = parseClientDateTime(fromStr);
        LocalDateTime toLdt   = parseClientDateTime(toStr);

        Timestamp from = Timestamp.valueOf(fromLdt);
        Timestamp to   = Timestamp.valueOf(toLdt);

        return eventDao.selectEventsByCalendarAndPeriod(calendarId, from, to);
    }

    @Transactional
    @Override
    public Long createEvent(Long userNo, EventDto.Req req) {
        // req.getStartAt(), getEndAt()는 Timestamp로 가정 (프런트와 DTO에서 변환됨)
        if (req.getRoomId() != null) {
            int conflicts = eventDao.countRoomConflicts(req.getRoomId(), req.getStartAt(), req.getEndAt());
            if (conflicts > 0) throw new ConflictException("회의실 시간이 겹칩니다.");
        }
        Long eventId = eventDao.insertEvent(userNo, req);
        saveParticipantsSafe(eventId, req);
        return eventId;
    }

    @Transactional
    @Override
    public void updateEvent(Long userNo, Long eventId, EventDto.Req req) {
        if (req.getRoomId() != null) {
            int conflicts = eventDao.countRoomConflictsExcludingSelf(eventId, req.getRoomId(), req.getStartAt(), req.getEndAt());
            if (conflicts > 0) throw new ConflictException("회의실 시간이 겹칩니다.");
        }
        int rows = eventDao.updateEvent(userNo, eventId, req);
        if (rows == 0) throw new IllegalStateException("수정 대상이 없거나 삭제된 일정입니다.");

        // 전체 갱신 전략
        eventDao.deleteParticipantsByEventId(eventId);
        saveParticipantsSafe(eventId, req);
    }

    @Transactional
    @Override
    public void deleteEvent(Long userNo, Long eventId) {
        int rows = eventDao.logicalDeleteEvent(userNo, eventId);
        if (rows == 0) throw new IllegalStateException("삭제 대상이 없거나 이미 삭제되었습니다.");

        // 회의실 예약 논리삭제(STATUS=DELETED) & 링크 해제
        conferenceRoomDao.deleteReservationsByEventId(eventId, userNo);
        eventDao.updateEventRoomLink(eventId, null, userNo);

        // 참석자/공유자 정리
        eventDao.deleteParticipantsByEventId(eventId);
    }

    /** 서비스 레벨 방어: null 제거, distinct, 교집합 제거 */
    private void saveParticipantsSafe(Long eventId, EventDto.Req req) {
        List<Long> attendees = toDistinct(req.getAttendeeUserNos());
        List<Long> sharers = toDistinct(req.getShareUserNos());

        // DB UNIQUE (EVENT_ID, USER_NO) 대비 교집합 제거
        sharers.removeAll(attendees);

        if (!attendees.isEmpty()) {
            eventDao.batchInsertParticipantsIfAbsent(eventId, attendees, "ATTENDEE");
        }
        if (!sharers.isEmpty()) {
            eventDao.batchInsertParticipantsIfAbsent(eventId, sharers, "SHARER");
        }
    }

    private static List<Long> toDistinct(List<Long> src) {
        if (src == null) return List.of();
        return src.stream().filter(Objects::nonNull).distinct().collect(Collectors.toList());
    }

    // 라벨
    @Override
    public List<EventDto.LabelRes> labelList() {
        return eventDao.selectAllLabels();
    }

    @Override
    @Transactional
    public Long labelCreate(EventDto.LabelReq req) {
        return eventDao.insertLabel(req);
    }

    @Override
    @Transactional
    public void labelUpdate(Long labelId, EventDto.LabelReq req) {
        if (eventDao.updateLabel(labelId, req) == 0) {
            throw new IllegalStateException("수정 대상이 없습니다.");
        }
    }

    @Override
    @Transactional
    public void labelDelete(Long labelId) {
        if (eventDao.deleteLabel(labelId) == 0) {
            throw new IllegalStateException("삭제 대상이 없습니다.");
        }
    }

    @Override
    public List<EventDto.EventTypeDto> getEventTypes() {
        return eventDao.selectAllEventTypes();
    }

    private String toCode(String name) {
        String base = name == null ? "" : name.trim();
        base = java.text.Normalizer.normalize(base, java.text.Normalizer.Form.NFD)
                .replaceAll("\\p{M}", "")
                .replaceAll("[^A-Za-z0-9]+", "_")
                .replaceAll("^_|_$", "")
                .toUpperCase();
        return base.isEmpty() ? "TYPE" : base;
    }

    @Override
    public EventDto.EventTypeDto createEventType(String typeName) {
        EventDto.EventTypeDto dto = EventDto.EventTypeDto.builder()
                .typeName(typeName)
                .typeCode(toCode(typeName))
                .build();
        eventDao.insertEventType(dto); // dto.typeId 채워짐
        return dto;
    }

    @Override
    public void updateEventType(Long typeId, String typeName) {
        eventDao.updateEventTypeName(EventDto.EventTypeDto.builder().typeId(typeId).typeName(typeName).build());
    }

    @Override
    public void deleteEventType(Long typeId) {
        eventDao.deleteEventType(typeId);
    }

    @Override
    @Transactional(readOnly = true)
    public EventDto.DetailRes getEventDetail(Long me, Long eventId) {
        EventDto.DetailRes res = eventDao.selectEventDetailById(eventId);
        if (res == null) return null;

        res.setAttendees(eventDao.selectAttendeesByEventId(eventId));
        res.setSharers(eventDao.selectSharersByEventId(eventId));
        
        boolean isAuthor = res.getCreateByUserNo() != null && res.getCreateByUserNo().equals(me);
        boolean isHrOrAdmin = hasAnyRole("ROLE_HR", "ROLE_ADMIN");

        res.setCanEdit(isAuthor || isHrOrAdmin);
        res.setCanDelete(isAuthor || isHrOrAdmin);
        return res;
    }

    private boolean hasAnyRole(String... roles) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null) return false;
        Set<String> wanted = Set.of(roles);
        for (GrantedAuthority ga : auth.getAuthorities()) {
            String name = ga.getAuthority();
            if (wanted.contains(name)) return true;
            if (wanted.contains("ROLE_" + name)) return true;
            if (wanted.contains(name.toUpperCase())) return true;
        }
        return false;
    }

    @Override
    @Transactional
    public void updateEventWithAuth(Long me, Long eventId, Object updateReq) {
        EventDto.DetailRes cur = eventDao.selectEventDetailById(eventId);
        if (cur == null) throw new ConflictException("이벤트가 존재하지 않습니다.");

        boolean isAuthor = (cur.getCreateByUserNo() != null && cur.getCreateByUserNo().equals(me));
        boolean isHrOrAdmin = eventDao.countHrOrAdmin(me) > 0;
        if (!(isAuthor || isHrOrAdmin)) {
            throw new ConflictException("수정 권한이 없습니다.");
        }
        // 기존 update 흐름 사용 (예: eventDao.updateEvent(...))
    }

    @Override
    @Transactional
    public void deleteEventWithAuth(Long me, Long eventId) {
        EventDto.DetailRes cur = eventDao.selectEventDetailById(eventId);
        if (cur == null) return;

        boolean isAuthor = (cur.getCreateByUserNo() != null && cur.getCreateByUserNo().equals(me));
        boolean isHrOrAdmin = eventDao.countHrOrAdmin(me) > 0;
        if (!(isAuthor || isHrOrAdmin)) {
            throw new ConflictException("삭제 권한이 없습니다.");
        }
        // 기존 논리삭제 흐름 사용 (예: eventDao.logicalDeleteEvent(eventId, me);)
    }

    @Override
    @Transactional
    public void updateEventRoomLink(Long userNo, Long eventId, Long roomId) {
        eventDao.updateEventRoomLink(eventId, roomId, userNo);
    }
}
