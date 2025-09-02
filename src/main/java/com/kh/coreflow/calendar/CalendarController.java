package com.kh.coreflow.calendar;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kh.coreflow.calendar.model.service.CalendarService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("")
@RequiredArgsConstructor
public class CalendarController {

	private final CalendarService calendarService;
	
	  // 결과 URL: /api/calendars/visible
    @GetMapping("/calendars/visible")
    public List<CalendarSummaryDTO> visibleCalendars(
            @RequestHeader(value = "X-User-No", required = false) String userNoHeader
    ) {
        Long me = parseUser(userNoHeader);
        return calendarService.getVisibleCalendars(me);
    }

    private Long parseUser(String h) {
        try { return Long.parseLong(h); } catch (Exception e) { return 1001L; }
    }
}