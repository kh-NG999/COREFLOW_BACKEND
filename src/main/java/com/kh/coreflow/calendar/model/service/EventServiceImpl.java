package com.kh.coreflow.calendar.model.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kh.coreflow.calendar.model.dao.EventDao;
import com.kh.coreflow.calendar.model.dto.EventDto;
import com.kh.coreflow.common.exception.ConflictException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {

    private final EventDao eventDao;

    @Override
    public List<EventDto.Res> getEvents(Long calendarId, LocalDateTime from, LocalDateTime to) {
        if (from == null || to == null || !from.isBefore(to))
            throw new IllegalArgumentException("from/to 기간이 유효하지 않습니다.");
        return eventDao.selectEventsByCalendarAndPeriod(calendarId, from, to);
    }

    @Override
    @Transactional
    public Long createEvent(Long userNo, EventDto.Create req) {
        validateReq(req);
        // 회의실 예약인 경우에만 겹침검사
        if (req.getRoomId() != null) {
            int conflicts = eventDao.countRoomConflicts(req.getRoomId(), req.getStartAt(), req.getEndAt());
            if (conflicts > 0) throw new ConflictException("해당 시간대에 이미 회의실 예약이 있습니다.");
        }
        eventDao.insertEvent(userNo, req); // selectKey로 req.eventId 채워짐
        return req.getEventId();
    }

    @Override
    @Transactional
    public void updateEvent(Long userNo, Long eventId, EventDto.Create req) {
        validateReq(req);
        if (req.getRoomId() != null) {
            int conflicts = eventDao.countRoomConflictsExcludingSelf(eventId, req.getRoomId(), req.getStartAt(), req.getEndAt());
            if (conflicts > 0) throw new ConflictException("해당 시간대에 이미 회의실 예약이 있습니다.");
        }
        eventDao.updateEvent(userNo, eventId, req);
    }

    @Override
    @Transactional
    public void deleteEvent(Long userNo, Long eventId) {
        eventDao.logicalDeleteEvent(userNo, eventId);
    }

    private void validateReq(EventDto.Create req) {
        if (req.getCalId() == null) throw new IllegalArgumentException("calId 필수");
        if (req.getTitle() == null || req.getTitle().isBlank()) throw new IllegalArgumentException("title 필수");
        if (req.getStartAt() == null || req.getEndAt() == null || !req.getStartAt().isBefore(req.getEndAt()))
            throw new IllegalArgumentException("startAt/endAt가 유효하지 않습니다.");
    }
}
