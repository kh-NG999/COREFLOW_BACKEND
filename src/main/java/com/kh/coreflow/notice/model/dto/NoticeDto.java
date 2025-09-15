package com.kh.coreflow.notice.model.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public class NoticeDto {
	
	// 공지 전체 조회
	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	public static class NoticeResponse{
		public long notiId;
		public long writer;
		public String title;
		public Date enrollDate;
		public String essential;
		public String status;
	}
}
