package com.kh.coreflow.calendar.model.dao;

import com.kh.coreflow.calendar.model.dto.EventDto;
import java.util.List;

public interface EventDao {
    List<EventDto.Res> selectEventsByCalendarAndPeriod(Long calendarId, java.sql.Timestamp from, java.sql.Timestamp to);

    int countRoomConflicts(Long roomId, java.sql.Timestamp startAt, java.sql.Timestamp endAt);
    int countRoomConflictsExcludingSelf(Long eventId, Long roomId, java.sql.Timestamp startAt, java.sql.Timestamp endAt);

    Long insertEvent(Long userNo, EventDto.Req req);
    int updateEvent(Long userNo, Long eventId, EventDto.Req req);
    int logicalDeleteEvent(Long userNo, Long eventId);
}
