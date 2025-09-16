package com.kh.coreflow.calendar.model.service;

import com.kh.coreflow.calendar.model.dto.EventDto;
import com.kh.coreflow.calendar.model.dto.EventDto.EventTypeDto;

import java.util.List;

public interface EventService {
    List<EventDto.Res> getEvents(Long userNo, Long calendarId, String fromIso, String toIso);
    Long createEvent(Long userNo, EventDto.Req req);
    void updateEvent(Long userNo, Long eventId, EventDto.Req req);
    void deleteEvent(Long userNo, Long eventId);
    
    //라벨
    List<EventDto.LabelRes> labelList();
    Long labelCreate(EventDto.LabelReq req);
    void labelUpdate(Long labelId, EventDto.LabelReq req);
    void labelDelete(Long labelId);
	
    List<EventDto.EventTypeDto> getEventTypes();
	EventTypeDto createEventType(String typeName);
	void updateEventType(Long typeId, String typeName);
	void deleteEventType(Long typeId);
	
	void updateEventWithAuth(Long me, Long eventId, Object updateReq);
    void deleteEventWithAuth(Long me, Long eventId);
    EventDto.DetailRes getEventDetail(Long userNo, Long eventId);
    void updateEventRoomLink(Long userNo, Long eventId, Long roomId);
    

}