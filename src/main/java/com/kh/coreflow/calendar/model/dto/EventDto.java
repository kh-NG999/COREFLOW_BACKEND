package com.kh.coreflow.calendar.model.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

public class EventDto {

	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	@Builder
	public static class Req {
		private Long eventId; // 업데이트 시 사용
		private Long calId;
		private String title;
		private java.sql.Timestamp startAt; // 'YYYY-MM-DDTHH:mm:ss' 파싱
		private java.sql.Timestamp endAt;
		private boolean allDay;

		private String locationText;
		private String note;
		private Long roomId;
		private String status; // 생성 시 서버에서 'CONFIRMED'로 세팅
		private Long labelId;
		private String eventType; // MEETING/PERSONAL/OOO 등
		private String rrule;
		private String exdates;
		private List<Long> attendeeUserNos;
		private List<Long> shareUserNos;
	}

	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	@Builder
	public static class Res {
		private Long eventId;
		private Long calId;
		private String title;
		private java.sql.Timestamp startAt;
		private java.sql.Timestamp endAt;
		private String allDayYn; // 'Y'/'N'
		private String locationText;
		private String note;
		private Long roomId;
		private String status;
		private Long labelId;
		private String eventType;
		private String rrule;
		private String exdates;
		private Long createByUserNo;
		private java.sql.Timestamp createDate;
		private Long updateUserNo;
		private java.sql.Timestamp updateDate;
	}
	
	@Data @NoArgsConstructor @AllArgsConstructor @Builder
	public static class LabelRes {
	    private Long labelId;
	    private String labelName;
	    private String labelColor; // "#RRGGBB"
	}

	@Data @NoArgsConstructor @AllArgsConstructor @Builder
	public static class LabelReq {
	    private String labelName;
	    private String labelColor;
	}
}
