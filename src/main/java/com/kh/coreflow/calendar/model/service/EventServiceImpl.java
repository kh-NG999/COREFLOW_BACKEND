package com.kh.coreflow.calendar.model.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import com.kh.coreflow.calendar.model.dao.CalendarDao;
import com.kh.coreflow.calendar.model.dao.EventDao;
import com.kh.coreflow.calendar.model.dto.EventDto;
import com.kh.coreflow.common.exception.ConflictException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {

    private final EventDao eventDao;
    private final CalendarDao calendarDao;

    private static final DateTimeFormatter ISO_LOCAL = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

    @Override
    public List<EventDto.Res> getEvents(Long userNo, Long calendarId, String fromIso, String toIso) {
        Timestamp from = Timestamp.valueOf(LocalDateTime.parse(fromIso));
        Timestamp to   = Timestamp.valueOf(LocalDateTime.parse(toIso));
        return eventDao.selectEventsByCalendarAndPeriod(calendarId, from, to);
    }

    @Transactional
    @Override
    public Long createEvent(Long userNo, EventDto.Req req) {
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

        // 전체 갱신 전략(필요시 delete→재삽입으로 바꾸셔도 됩니다)
        eventDao.deleteParticipantsByEventId(eventId);
        saveParticipantsSafe(eventId, req);
    }

    @Transactional
    @Override
    public void deleteEvent(Long userNo, Long eventId) {
        int rows = eventDao.logicalDeleteEvent(userNo, eventId);
        if (rows == 0) throw new IllegalStateException("삭제 대상이 없거나 이미 삭제되었습니다.");
        eventDao.deleteParticipantsByEventId(eventId);
    }

    /** 서비스 레벨 방어: null 제거, distinct, 교집합 제거 */
    private void saveParticipantsSafe(Long eventId, EventDto.Req req) {
        List<Long> attendees = toDistinct(req.getAttendeeUserNos());
        List<Long> sharers   = toDistinct(req.getShareUserNos());

        // 만약 DB UNIQUE가 (EVENT_ID, USER_NO)이면 교집합 제거가 필수
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
    
    //라벨
    @Override
    public List<EventDto.LabelRes> labelList() {
        return eventDao.selectAllLabels();
    }

    @Override @Transactional
    public Long labelCreate(EventDto.LabelReq req) {
        return eventDao.insertLabel(req);
    }

    @Override @Transactional
    public void labelUpdate(Long labelId, EventDto.LabelReq req) {
        if (eventDao.updateLabel(labelId, req) == 0) {
            throw new IllegalStateException("수정 대상이 없습니다.");
        }
    }

    @Override @Transactional
    public void labelDelete(Long labelId) {
        if (eventDao.deleteLabel(labelId) == 0) {
            throw new IllegalStateException("삭제 대상이 없습니다.");
        }
    }
    
    
}

















