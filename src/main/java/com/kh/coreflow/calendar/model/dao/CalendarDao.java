package com.kh.coreflow.calendar.model.dao;

import com.kh.coreflow.calendar.model.dto.CalendarDto;

import java.util.List;

public interface CalendarDao {
    List<CalendarDto.SummaryRes> selectVisibleCalendars(Long userNo);
    CalendarDto.DetailRes selectById(Long calId);

    Long insertCalendar(Long ownerUserNo, CalendarDto.CreateReq req);
    int updateCalendar(Long ownerUserNo, Long calId, CalendarDto.UpdateReq req);
    int deleteCalendar(Long ownerUserNo, Long calId);

    boolean existsCalendarAccess(Long userNo, Long calId);
}
