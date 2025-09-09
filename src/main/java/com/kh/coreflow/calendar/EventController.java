package com.kh.coreflow.calendar;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.kh.coreflow.calendar.model.dto.EventDto;
import com.kh.coreflow.calendar.model.service.EventService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/** 최종 URL: /api/events/... */
@RestController
@RequestMapping("/events")
@RequiredArgsConstructor
@Slf4j
public class EventController {

	private final EventService eventService;
    public record EventIdResponse(Long eventId) {}


    @GetMapping
    public ResponseEntity<List<EventDto.Res>> list(
            @AuthenticationPrincipal Integer me,
            @RequestParam Long calendarId,
            @RequestParam String from,
            @RequestParam String to
    ) {
        if (me == null) return ResponseEntity.status(401).build();
        return ResponseEntity.ok(eventService.getEvents(me.longValue(), calendarId, from, to));
    }

    @PostMapping
    public ResponseEntity<EventIdResponse> create(
            @AuthenticationPrincipal Integer me,
            @Valid @RequestBody EventDto.Req req
    ) {
        if (me == null) return ResponseEntity.status(401).build();

        Long id = eventService.createEvent(me.longValue(), req);

        // ✅ 현재 요청(/api/events) 기준으로 Location: /api/events/{id} 생성
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(id)
                .toUri();

        return ResponseEntity
                .created(location)                // 201 Created + Location 헤더
                .body(new EventIdResponse(id));   // { "eventId": 123 }
    }

    @PutMapping("/{eventId}")
    public ResponseEntity<Void> update(
            @AuthenticationPrincipal Integer me,
            @PathVariable Long eventId,
            @Valid @RequestBody EventDto.Req req
    ) {
        if (me == null) return ResponseEntity.status(401).build();
        eventService.updateEvent(me.longValue(), eventId, req);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{eventId}")
    public ResponseEntity<Void> delete(
            @AuthenticationPrincipal Integer me,
            @PathVariable Long eventId
    ) {
        if (me == null) return ResponseEntity.status(401).build();
        eventService.deleteEvent(me.longValue(), eventId);
        return ResponseEntity.noContent().build();
    }
    
    @RestController
    @RequestMapping("/labels") // 최종 경로: /api/labels
    @RequiredArgsConstructor
    class LabelController {

        private final EventService eventService;

        @GetMapping
        public ResponseEntity<List<EventDto.LabelRes>> list() {
            return ResponseEntity.ok(eventService.labelList());
        }

        @PostMapping
        public ResponseEntity<EventDto.LabelRes> create(@RequestBody EventDto.LabelReq req) {
            Long id = eventService.labelCreate(req);
            return ResponseEntity.ok(new EventDto.LabelRes(id, req.getLabelName(), req.getLabelColor()));
        }

        @PutMapping("/{labelId}")
        public ResponseEntity<EventDto.LabelRes> update(@PathVariable Long labelId,
                                                        @RequestBody EventDto.LabelReq req) {
            eventService.labelUpdate(labelId, req);
            return ResponseEntity.ok(new EventDto.LabelRes(labelId, req.getLabelName(), req.getLabelColor()));
        }

        @DeleteMapping("/{labelId}")
        public ResponseEntity<Void> delete(@PathVariable Long labelId) {
            eventService.labelDelete(labelId);
            return ResponseEntity.noContent().build();
        }
    }
    
    
}

















