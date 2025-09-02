package com.kh.coreflow.calendar.model.dto;

import java.time.LocalDateTime;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * FullCalendar 연동용 DTO
 * - 응답: id, title, start, end, allDay, extendedProps
 * - 요청: start/end는 ISO 문자열. allDay인 경우 end는 FC 특성상 "다음날 00:00"이 올 수 있음.
 */
public class FcEventDto {

    @Data @NoArgsConstructor @AllArgsConstructor @Builder
    public static class Res {
        private String id;            // EVENT_ID 문자열
        private String title;
        private String start;         // ISO-8601 문자열
        private String end;           // ISO-8601 문자열
        private boolean allDay;
        private Map<String, Object> extendedProps; // roomId, calId, note, labelId, eventType, locationText 등
    }

    @Data @NoArgsConstructor @AllArgsConstructor @Builder
    public static class CreateReq {
        // 프론트에서 오는 필드 이름(FC 표준 + 우리 확장)
        private Long calId;
        private String title;
        private String start;     // ISO
        private String end;       // ISO
        private Boolean allDay;   // optional (null이면 false)
        private String locationText;
        private String note;
        private Long roomId;
        private String eventType;
        private Long labelId;
        private String rrule;
        private String exdates;
    }

    @Data @NoArgsConstructor @AllArgsConstructor @Builder
    public static class UpdateReq {
        private String title;
        private String start;
        private String end;
        private Boolean allDay;
        private String locationText;
        private String note;
        private Long roomId;
        private String eventType;
        private Long labelId;
        private String rrule;
        private String exdates;
    }

    // ----- 변환 유틸 -----

    public static EventDto.Create toCreate(EventDto.TimeConverter tc, CreateReq req) {
        LocalDateTime startAt = tc.parse(req.getStart());
        LocalDateTime endAt   = tc.parse(req.getEnd());
        boolean allDay = Boolean.TRUE.equals(req.getAllDay());
        // FC는 allDay에서 end가 "종료일 다음날 00:00"로 오기 때문에, 필요시 -1초 보정 가능
        // 여기서는 DB에는 그대로 저장(프론트에서 같은 규칙으로 표시됨)

        return EventDto.Create.builder()
                .calId(req.getCalId())
                .title(req.getTitle())
                .startAt(startAt)
                .endAt(endAt)
                .allDay(allDay)
                .locationText(req.getLocationText())
                .note(req.getNote())
                .roomId(req.getRoomId())
                .eventType(req.getEventType())
                .labelId(req.getLabelId())
                .rrule(req.getRrule())
                .exdates(req.getExdates())
                .build();
    }

    public static EventDto.Create toUpdate(EventDto.TimeConverter tc, UpdateReq req, Long calId) {
        LocalDateTime startAt = tc.parse(req.getStart());
        LocalDateTime endAt   = tc.parse(req.getEnd());
        boolean allDay = Boolean.TRUE.equals(req.getAllDay());

        return EventDto.Create.builder()
                .calId(calId)
                .title(req.getTitle())
                .startAt(startAt)
                .endAt(endAt)
                .allDay(allDay)
                .locationText(req.getLocationText())
                .note(req.getNote())
                .roomId(req.getRoomId())
                .eventType(req.getEventType())
                .labelId(req.getLabelId())
                .rrule(req.getRrule())
                .exdates(req.getExdates())
                .build();
    }

    public static Res fromEvent(EventDto.Res e, EventDto.TimeConverter tc) {
        boolean allDay = "Y".equalsIgnoreCase(e.getAllDayYn());
        return Res.builder()
                .id(String.valueOf(e.getEventId()))
                .title(e.getTitle())
                .start(tc.format(e.getStartAt(), allDay))
                .end(tc.format(e.getEndAt(), allDay))
                .allDay(allDay)
                .extendedProps(Map.of(
                        "calId", e.getCalId(),
                        "roomId", e.getRoomId(),
                        "labelId", e.getLabelId(),
                        "eventType", e.getEventType(),
                        "locationText", e.getLocationText(),
                        "note", e.getNote(),
                        "status", e.getStatus()
                ))
                .build();
    }
}