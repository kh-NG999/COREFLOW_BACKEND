package com.kh.coreflow.notice.model.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public class NoticeDto {
	
	// 공지 조회
	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	public static class NoticeResponse{
		public long notiId;
		public String userName;
		public String title;
		public String enrollDate;
		public String essential;
		public String status;
	}
	
	// 공지 검색(제목, 내용, 작성자)
	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	public static class NoticeSearch{
		private String searchType;
		private String keyword;
	}
	
	// 공지 상세 조회
	// 첨부파일은 일단 생략
	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	public static class NoticeDetail{
		필독여부,
		제목,
		작성자,
		부서,
		직위,
		작성일,
		내용
		
		
	}
