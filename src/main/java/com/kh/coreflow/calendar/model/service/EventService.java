package com.kh.coreflow.calendar.model.service;

import java.time.LocalDateTime;
import java.util.List;

import com.kh.coreflow.calendar.model.dto.EventDto;

public interface EventService {

    // 내부 도메인
    List<EventDto.Res> getEvents(Long calendarId, LocalDateTime from, LocalDateTime to);
    Long createEvent(Long userNo, EventDto.Create req);
    void updateEvent(Long userNo, Long eventId, EventDto.Create req);
    void deleteEvent(Long userNo, Long eventId);
}
