package com.kh.coreflow.calendar.model.service;

import com.kh.coreflow.calendar.model.dao.CalendarDao;
import com.kh.coreflow.calendar.model.dto.CalendarDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CalendarServiceImpl implements CalendarService {

    private final CalendarDao calendarDao;

    @Override
    public List<CalendarDto.SummaryRes> getVisibleCalendars(Long userNo) {
        return calendarDao.selectVisibleCalendars(userNo);
    }

    @Override
    public CalendarDto.DetailRes getCalendar(Long calId) {
        return calendarDao.selectById(calId);
    }

    @Transactional
    @Override
    public Long createCalendar(Long ownerUserNo, CalendarDto.CreateReq req) {
        return calendarDao.insertCalendar(ownerUserNo, req);
    }

    @Transactional
    @Override
    public void updateCalendar(Long ownerUserNo, Long calId, CalendarDto.UpdateReq req) {
        int rows = calendarDao.updateCalendar(ownerUserNo, calId, req);
        if (rows == 0) throw new IllegalStateException("수정 대상이 없거나 권한이 없습니다.");
    }

    @Transactional
    @Override
    public void deleteCalendar(Long ownerUserNo, Long calId) {
        int rows = calendarDao.deleteCalendar(ownerUserNo, calId);
        if (rows == 0) throw new IllegalStateException("삭제 대상이 없거나 권한이 없습니다.");
    }
}
