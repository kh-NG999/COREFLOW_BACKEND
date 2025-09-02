package com.kh.coreflow.calendar.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

public class CalendarDto {

	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	@Builder
	public static class CalendarSummaryDTO {
		
		private Long   calId;          // 캘린더 ID
		private String calName;        // 캘린더 이름
		private String color;           // 색상 (예: #4285F4)
		private Integer isPersonal;    // 계산필드: 1/0
		private Integer defaultForMe; // 계산필드: 1/0
	}
	
	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	@Builder
	/* fullcalendar에 전달할 응답 */
	public static class FcCalendarRes {
		private Long calId;
		private String name;
		private String color;
		private Boolean isPersonal;
		private Boolean defaultForMe;
	}
}
