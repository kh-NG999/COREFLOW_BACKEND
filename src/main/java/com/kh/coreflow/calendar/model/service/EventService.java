package com.kh.coreflow.calendar.model.service;

import com.kh.coreflow.calendar.model.dto.EventDto;
import java.util.List;

public interface EventService {
    List<EventDto.Res> getEvents(Long userNo, Long calendarId, String fromIso, String toIso);
    Long createEvent(Long userNo, EventDto.Req req);
    void updateEvent(Long userNo, Long eventId, EventDto.Req req);
    void deleteEvent(Long userNo, Long eventId);
}