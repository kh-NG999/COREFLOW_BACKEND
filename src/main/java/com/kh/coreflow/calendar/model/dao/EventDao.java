package com.kh.coreflow.calendar.model.dao;

import java.time.LocalDateTime;
import java.util.List;

import com.kh.coreflow.calendar.model.dto.EventDto;

public interface EventDao {

    List<EventDto.Res> selectEventsByCalendarAndPeriod(Long calendarId, LocalDateTime from, LocalDateTime to);
    int countRoomConflicts(Long roomId, LocalDateTime startAt, LocalDateTime endAt);
    int countRoomConflictsExcludingSelf(Long eventId, Long roomId, LocalDateTime startAt, LocalDateTime endAt);
    int insertEvent(Long userNo, EventDto.Create req);
    int updateEvent(Long userNo, Long eventId, EventDto.Create req);
    int logicalDeleteEvent(Long userNo, Long eventId);
}
