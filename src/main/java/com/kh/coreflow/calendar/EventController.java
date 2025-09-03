package com.kh.coreflow.calendar;

import com.kh.coreflow.calendar.model.dto.EventDto;
import com.kh.coreflow.calendar.model.service.EventService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

/** 최종 URL: /api/events/... */
@RestController
@RequestMapping("/events")
@RequiredArgsConstructor
@Slf4j
public class EventController {

    private final EventService eventService;

    // 기간 내 일정 조회
    @GetMapping
    public ResponseEntity<List<EventDto.Res>> events(
            @RequestHeader(value = "X-User-No", required = false, defaultValue = "0") Long userNo,
            @RequestParam Long calendarId,
            @RequestParam(required = false) String from, // 'YYYY-MM-DDTHH:mm:ss'
            @RequestParam(required = false) String to
    ) {
        return ResponseEntity.ok(eventService.getEvents(userNo, calendarId, from, to));
    }

    // 일정 생성
    @PostMapping
    public ResponseEntity<Long> create(
            @RequestHeader(value = "X-User-No", required = false, defaultValue = "0") Long userNo,
            @Valid @RequestBody EventDto.Req req
    ) {
        Long id = eventService.createEvent(userNo, req);
        return ResponseEntity.ok(id);
    }

    // 일정 수정
    @PutMapping("/{eventId}")
    public ResponseEntity<Void> update(
            @RequestHeader(value = "X-User-No", required = false, defaultValue = "0") Long userNo,
            @PathVariable Long eventId,
            @Valid @RequestBody EventDto.Req req
    ) {
        eventService.updateEvent(userNo, eventId, req);
        return ResponseEntity.noContent().build();
    }

    // 일정 삭제(논리삭제)
    @DeleteMapping("/{eventId}")
    public ResponseEntity<Void> delete(
            @RequestHeader(value = "X-User-No", required = false, defaultValue = "0") Long userNo,
            @PathVariable Long eventId
    ) {
        eventService.deleteEvent(userNo, eventId);
        return ResponseEntity.noContent().build();
    }
}