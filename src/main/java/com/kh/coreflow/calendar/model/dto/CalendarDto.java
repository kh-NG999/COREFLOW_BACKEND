package com.kh.coreflow.calendar.model.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
        private Long depId;
        private Long ownerUserNo;
        private String calName;
        private String color;
        private String defaultRole;
        private String deletedYn;
        private Date createDate;
        private Date updateDate;
	}

	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	@Builder
	public static class CreateReq {
		private String name;
		private String color;
		private String defaultRole; // 기본 공유 권한
		private Long depId; // 선택
	}

	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	@Builder
	 public static class CreateRes {
        private Long calId;
        private String name;           // = CAL_NAME
        private String color;
        private String defaultRole;
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
