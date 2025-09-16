package com.kh.coreflow.calendar.model.dto;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSetter;

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
		private Long typeId; // MEETING/PERSONAL/OOO 등
		private String typeCode;
		private String rrule;
		private String exdates;
		private List<Long> attendeeUserNos;
		private List<Long> shareUserNos;
		
		  @JsonIgnore
		  private static final DateTimeFormatter SPACE_FMT =
		      DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

		  @JsonSetter("startAt")
		  public void jsonSetStartAt(Object v) { this.startAt = parseToTimestamp(v); }

		  @JsonSetter("endAt")
		  public void jsonSetEndAt(Object v) { this.endAt = parseToTimestamp(v); }

		  private static Timestamp parseToTimestamp(Object v) {
		    if (v == null) return null;
		    if (v instanceof Timestamp ts) return ts;

		    String s = String.valueOf(v).trim();
		    if (s.isEmpty()) return null;

		    // 1) 공백 포맷 "yyyy-MM-dd HH:mm:ss"
		    try {
		      return Timestamp.valueOf(LocalDateTime.parse(s, SPACE_FMT));
		    } catch (Exception ignore) {}

		    // 2) ISO 포맷 "yyyy-MM-dd'T'HH:mm:ss"
		    try {
		      return Timestamp.valueOf(LocalDateTime.parse(s));
		    } catch (Exception ignore) {}

		    // 3) 보정: 10번째 문자가 공백이면 'T'로 바꿔 재시도
		    if (s.length() > 10 && s.charAt(10) == ' ') {
		      String iso = s.substring(0, 10) + 'T' + s.substring(11);
		      try {
		        return Timestamp.valueOf(LocalDateTime.parse(iso));
		      } catch (Exception ignore) {}
		    }

		    throw new IllegalArgumentException("잘못된 날짜/시간 형식: " + s);
		  }
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
		private Long typeId;
		private String typeName;
		private String typeCode;
		private String rrule;
		private String exdates;
		private Long createByUserNo;
		private java.sql.Timestamp createDate;
		private Long updateUserNo;
		private java.sql.Timestamp updateDate;
	}

	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	@Builder
	public static class LabelRes {
		private Long labelId;
		private String labelName;
		private String labelColor; // "#RRGGBB"
	}

	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	@Builder
	public static class LabelReq {
		private String labelName;
		private String labelColor;
	}

	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	@Builder
	public static class EventTypeDto {
		private Long typeId;
		private String typeCode;
		private String typeName;
	}

	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	@Builder
	public static class RoomSummary {
		private Long roomId;
		private String roomName;
		private String buildingName;
		private String floor;
		private String roomNo;
		private Integer capacity;
		private String detailLocation; // SVG 절대 URL (미리보기 경로)
	}

	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	@Builder
	public static class DetailRes {
		private Long eventId;
		private Long calId;
		private String title;
		private String startAt; // "YYYY-MM-DDTHH:mm:ss"
		private String endAt; // "
		private String allDayYn; // "Y" | "N"
		private String locationText;
		private String note;
		private Long roomId;
		private String status;
		private Long labelId;
		private String typeId;
		private Long createByUserNo;

		// 회의실 요약
		private RoomSummary room;

		// 권한 플래그
		private boolean canEdit;
		private boolean canDelete;
		private java.util.List<Member> attendees;
		private java.util.List<Member> sharers;
	}

	@Data @NoArgsConstructor @AllArgsConstructor @Builder
	public static class Member {
	  private Long userNo;
	  private String userName;
	  private String email;
	  private Long depId;
	}
}
