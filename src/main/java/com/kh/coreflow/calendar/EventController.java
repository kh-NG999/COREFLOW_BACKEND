package com.kh.coreflow.calendar;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kh.coreflow.calendar.model.dto.EventDto;
import com.kh.coreflow.calendar.model.dto.FcEventDto;
import com.kh.coreflow.calendar.model.dto.FcEventDto.CreateReq;
import com.kh.coreflow.calendar.model.dto.FcEventDto.Res;
import com.kh.coreflow.calendar.model.dto.FcEventDto.UpdateReq;
import com.kh.coreflow.calendar.model.service.EventService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("")
@RequiredArgsConstructor
@Slf4j
public class EventController {

	private final EventService eventService;

	// ===== 1) 내부 도메인 API =====

	@GetMapping("/events")
	public ResponseEntity<List<EventDto.Res>> list(@RequestParam("calendarId") Long calendarId,
			@RequestParam("from") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime from,
			@RequestParam("to") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime to) {
		return ResponseEntity.ok(eventService.getEvents(calendarId, from, to));
	}

	@PostMapping("/events")
	public ResponseEntity<Long> create(@RequestHeader(value = "X-User-No", required = false) Long userNo,
			@RequestBody EventDto.Create req) {
		if (userNo == null)
			userNo = 0L;
		Long id = eventService.createEvent(userNo, req);
		return ResponseEntity.ok(id);
	}

	@PutMapping("/events/{eventId}")
	public ResponseEntity<Void> update(@RequestHeader(value = "X-User-No", required = false) Long userNo,
			@PathVariable Long eventId, @RequestBody EventDto.Create req) {
		if (userNo == null)
			userNo = 0L;
		eventService.updateEvent(userNo, eventId, req);
		return ResponseEntity.ok().build();
	}

	@DeleteMapping("/events/{eventId}")
	public ResponseEntity<Void> delete(@RequestHeader(value = "X-User-No", required = false) Long userNo,
			@PathVariable Long eventId) {
		if (userNo == null)
			userNo = 0L;
		eventService.deleteEvent(userNo, eventId);
		return ResponseEntity.ok().build();
	}

	// ===== 2) FullCalendar 전용 API =====
	// FullCalendar는 쿼리 파라미터로 start, end (ISO 문자열)를 보냄.

	@GetMapping("/fc/events")
	public ResponseEntity<List<Res>> fcList(@RequestParam("calendarId") Long calendarId,
			@RequestParam("start") String startIso, @RequestParam("end") String endIso) {
		var tc = EventDto.seoul(); // Asia/Seoul
		LocalDateTime from = tc.parse(startIso);
		LocalDateTime to = tc.parse(endIso);

		List<EventDto.Res> domain = eventService.getEvents(calendarId, from, to);
		List<Res> fc = domain.stream().map(e -> FcEventDto.fromEvent(e, tc)).collect(Collectors.toList());
		return ResponseEntity.ok(fc);
	}

	@PostMapping("/fc/events")
	public ResponseEntity<Long> fcCreate(@RequestHeader(value = "X-User-No", required = false) Long userNo,
			@RequestBody CreateReq req) {
		if (userNo == null)
			userNo = 0L;
		var tc = EventDto.seoul();
		EventDto.Create create = FcEventDto.toCreate(tc, req);
		Long id = eventService.createEvent(userNo, create);
		return ResponseEntity.ok(id);
	}

	@PutMapping("/fc/events/{eventId}")
	public ResponseEntity<Void> fcUpdate(@RequestHeader(value = "X-User-No", required = false) Long userNo,
			@PathVariable Long eventId, @RequestParam("calendarId") Long calendarId, // FC가 calId를 안줄 수도 있으면 body에
																						// 포함시키세요
			@RequestBody UpdateReq req) {
		if (userNo == null)
			userNo = 0L;
		var tc = EventDto.seoul();
		EventDto.Create update = FcEventDto.toUpdate(tc, req, calendarId);
		eventService.updateEvent(userNo, eventId, update);
		return ResponseEntity.ok().build();
	}

	@DeleteMapping("/fc/events/{eventId}")
	public ResponseEntity<Void> fcDelete(@RequestHeader(value = "X-User-No", required = false) Long userNo,
			@PathVariable Long eventId) {
		if (userNo == null)
			userNo = 0L;
		eventService.deleteEvent(userNo, eventId);
		return ResponseEntity.ok().build();
	}
}