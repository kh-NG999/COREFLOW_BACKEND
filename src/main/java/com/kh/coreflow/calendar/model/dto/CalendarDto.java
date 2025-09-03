package com.kh.coreflow.calendar.model.dto;

import lombok.*;

public class CalendarDto {

	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	@Builder
	public static class SummaryRes {
		private Long calId;
		private String calName;
		private String color;
		private Integer isPersonal; // 1/0
		private Integer defaultForMe; // 1/0
	}

	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	@Builder
	public static class DetailRes {
		private Long calId;
		private String calName;
		private String color;
		private String defaultRole; // NONE / READER / CONTRIBUTOR / EDITOR
		private Long ownerUserNo;
		private Long deptId;
		private String deletedYn; // 'Y'/'N'
		private java.sql.Timestamp createDate;
		private java.sql.Timestamp updateDate;
	}

	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	@Builder
	public static class CreateReq {
		private String name;
		private String color;
		private String defaultRole; // 기본 공유 권한
		private Long deptId; // 선택
	}

	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	@Builder
	public static class UpdateReq {
		private String name;
		private String color;
		private String defaultRole;
	}
}
