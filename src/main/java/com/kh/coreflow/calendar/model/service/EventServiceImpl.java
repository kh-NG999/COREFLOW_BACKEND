package com.kh.coreflow.calendar.model.service;

import java.time.LocalDateTime;
import java.util.List;

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
        if (!calendarDao.existsCalendarAccess(userNo, calendarId)) {
            throw new SecurityException("해당 캘린더 조회 권한이 없습니다.");
        }
        Timestamp from = (fromIso == null || fromIso.isEmpty()) ? Timestamp.valueOf("1900-01-01 00:00:00")
                : Timestamp.valueOf(LocalDateTime.parse(fromIso, ISO_LOCAL));
        Timestamp to = (toIso == null || toIso.isEmpty()) ? Timestamp.valueOf("2999-12-31 23:59:59")
                : Timestamp.valueOf(LocalDateTime.parse(toIso, ISO_LOCAL));

        return eventDao.selectEventsByCalendarAndPeriod(calendarId, from, to);
    }

    @Transactional
    @Override
    public Long createEvent(Long userNo, EventDto.Req req) {
        if (!calendarDao.existsCalendarAccess(userNo, req.getCalId())) {
            throw new SecurityException("해당 캘린더 생성 권한이 없습니다.");
        }
        if (req.getRoomId() != null) {
            int conflicts = eventDao.countRoomConflicts(req.getRoomId(), req.getStartAt(), req.getEndAt());
            if (conflicts > 0) throw new ConflictException("회의실 시간이 겹칩니다.");
        }
        return eventDao.insertEvent(userNo, req);
    }

    @Transactional
    @Override
    public void updateEvent(Long userNo, Long eventId, EventDto.Req req) {
        if (!calendarDao.existsCalendarAccess(userNo, req.getCalId())) {
            throw new SecurityException("해당 캘린더 수정 권한이 없습니다.");
        }
        if (req.getRoomId() != null) {
            int conflicts = eventDao.countRoomConflictsExcludingSelf(eventId, req.getRoomId(), req.getStartAt(), req.getEndAt());
            if (conflicts > 0) throw new ConflictException("회의실 시간이 겹칩니다.");
        }
        int rows = eventDao.updateEvent(userNo, eventId, req);
        if (rows == 0) throw new IllegalStateException("수정 대상이 없거나 삭제된 일정입니다.");
    }

    @Transactional
    @Override
    public void deleteEvent(Long userNo, Long eventId) {
        int rows = eventDao.logicalDeleteEvent(userNo, eventId);
        if (rows == 0) throw new IllegalStateException("삭제 대상이 없거나 이미 삭제되었습니다.");
    }
}
