package com.kh.coreflow.calendar.model.dao;

import com.kh.coreflow.calendar.model.dto.CalendarDto;
import lombok.RequiredArgsConstructor;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class CalendarDaoImpl implements CalendarDao {

    private final SqlSessionTemplate sql;

    @Override
    public List<CalendarDto.SummaryRes> selectVisibleCalendars(Long userNo) {
        Map<String,Object> p = new HashMap<>();
        p.put("userNo", userNo);
        return sql.selectList("calendar.selectVisibleCalendars", p);
    }

    @Override
    public CalendarDto.DetailRes selectById(Long calId) {
        Map<String,Object> p = new HashMap<>();
        p.put("calId", calId);
        return sql.selectOne("calendar.selectById", p);
    }

    @Override
    public Long insertCalendar(Long ownerUserNo, CalendarDto.CreateReq req) {
        Map<String,Object> p = new HashMap<>();
        p.put("ownerUserNo", ownerUserNo);
        p.put("name", req.getName());
        p.put("color", req.getColor());
        p.put("defaultRole", req.getDefaultRole());
        p.put("deptId", req.getDepId());
        sql.insert("calendar.insertCalendar", p); // selectKey로 calId 채워짐
        return (Long)p.get("calId");
    }

    @Override
    public int updateCalendar(Long ownerUserNo, Long calId, CalendarDto.UpdateReq req) {
        Map<String,Object> p = new HashMap<>();
        p.put("ownerUserNo", ownerUserNo);
        p.put("calId", calId);
        p.put("name", req.getName());
        p.put("color", req.getColor());
        p.put("defaultRole", req.getDefaultRole());
        return sql.update("calendar.updateCalendar", p);
    }

    @Override
    public int deleteCalendar(Long ownerUserNo, Long calId) {
        Map<String,Object> p = new HashMap<>();
        p.put("ownerUserNo", ownerUserNo);
        p.put("calId", calId);
        return sql.update("calendar.deleteCalendar", p);
    }

    @Override
    public boolean existsCalendarAccess(Long userNo, Long calId) {
        Map<String,Object> p = new HashMap<>();
        p.put("userNo", userNo);
        p.put("calId", calId);
        Integer cnt = sql.selectOne("calendar.existsCalendarAccess", p);
        return cnt != null && cnt > 0;
    }
    
}
