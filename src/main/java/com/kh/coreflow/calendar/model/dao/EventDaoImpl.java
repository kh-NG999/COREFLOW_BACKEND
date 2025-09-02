package com.kh.coreflow.calendar.model.dao;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import com.kh.coreflow.calendar.model.dto.EventDto;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class EventDaoImpl implements EventDao {

    private final SqlSessionTemplate sql;

    @Override
    public List<EventDto.Res> selectEventsByCalendarAndPeriod(Long calendarId, LocalDateTime from, LocalDateTime to) {
        Map<String,Object> p = new HashMap<>();
        p.put("calendarId", calendarId);
        p.put("from", from);
        p.put("to", to);
        return sql.selectList("event.selectEventsByCalendarAndPeriod", p);
    }

    @Override
    public int countRoomConflicts(Long roomId, LocalDateTime startAt, LocalDateTime endAt) {
        Map<String,Object> p = new HashMap<>();
        p.put("roomId", roomId);
        p.put("startAt", startAt);
        p.put("endAt", endAt);
        return sql.selectOne("event.countRoomConflicts", p);
    }

    @Override
    public int countRoomConflictsExcludingSelf(Long eventId, Long roomId, LocalDateTime startAt, LocalDateTime endAt) {
        Map<String,Object> p = new HashMap<>();
        p.put("eventId", eventId);
        p.put("roomId", roomId);
        p.put("startAt", startAt);
        p.put("endAt", endAt);
        return sql.selectOne("event.countRoomConflictsExcludingSelf", p);
    }

    @Override
    public int insertEvent(Long userNo, EventDto.Create req) {
        Map<String,Object> p = new HashMap<>();
        p.put("userNo", userNo);
        p.put("req", req);
        return sql.insert("event.insertEvent", p);
    }

    @Override
    public int updateEvent(Long userNo, Long eventId, EventDto.Create req) {
        Map<String,Object> p = new HashMap<>();
        p.put("userNo", userNo);
        p.put("eventId", eventId);
        p.put("req", req);
        return sql.update("event.updateEvent", p);
    }

    @Override
    public int logicalDeleteEvent(Long userNo, Long eventId) {
        Map<String,Object> p = new HashMap<>();
        p.put("userNo", userNo);
        p.put("eventId", eventId);
        return sql.update("event.logicalDeleteEvent", p);
    }
}
