package com.kh.coreflow.calendar.model.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FcEventDto {
    private String id;      // eventId 문자열화
    private String title;
    private String start;   // ISO string
    private String end;
    private boolean allDay;
}