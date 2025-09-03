package com.kh.coreflow.calendar;

import com.kh.coreflow.calendar.model.dto.CalendarDto;
import com.kh.coreflow.calendar.model.service.CalendarService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

/** 최종 URL: /api/calendars/... (server.servlet.context-path=/api) */
@RestController
@RequestMapping("/calendars")
@RequiredArgsConstructor
@Slf4j
public class CalendarController {

    private final CalendarService calendarService;

    // 내가 볼 수 있는 캘린더 목록
    @GetMapping("/visible")
    public ResponseEntity<List<CalendarDto.SummaryRes>> visibleCalendars(
            @RequestHeader(value = "X-User-No", defaultValue = "0") Long userNo
    ) {
        return ResponseEntity.ok(calendarService.getVisibleCalendars(userNo));
    }

    // 캘린더 단건 조회
    @GetMapping("/{calId}")
    public ResponseEntity<CalendarDto.DetailRes> getCalendar(@PathVariable Long calId) {
        return ResponseEntity.ok(calendarService.getCalendar(calId));
    }

    // 캘린더 생성(소유자 = 요청자)
    @PostMapping
    public ResponseEntity<Long> create(
            @RequestHeader(value = "X-User-No", defaultValue = "0") Long userNo,
            @Valid @RequestBody CalendarDto.CreateReq req
    ) {
        Long calId = calendarService.createCalendar(userNo, req);
        return ResponseEntity.ok(calId);
    }

    // 캘린더 수정(소유자만)
    @PutMapping("/{calId}")
    public ResponseEntity<Void> update(
            @RequestHeader(value = "X-User-No", defaultValue = "0") Long userNo,
            @PathVariable Long calId,
            @Valid @RequestBody CalendarDto.UpdateReq req
    ) {
        calendarService.updateCalendar(userNo, calId, req);
        return ResponseEntity.noContent().build();
    }

    // 캘린더 삭제(소유자만, 논리삭제)
    @DeleteMapping("/{calId}")
    public ResponseEntity<Void> delete(
            @RequestHeader(value = "X-User-No", defaultValue = "0") Long userNo,
            @PathVariable Long calId
    ) {
        calendarService.deleteCalendar(userNo, calId);
        return ResponseEntity.noContent().build();
    }
}