package com.kh.coreflow.calendar.model.service;

import java.util.List;

import com.kh.coreflow.calendar.model.dto.CalendarDto;
import java.util.List;

public interface CalendarService {
    List<CalendarDto.SummaryRes> getVisibleCalendars(Long userNo);
    CalendarDto.DetailRes getCalendar(Long calId);

    Long createCalendar(Long ownerUserNo, CalendarDto.CreateReq req);
    void updateCalendar(Long ownerUserNo, Long calId, CalendarDto.UpdateReq req);
    void deleteCalendar(Long ownerUserNo, Long calId);
}