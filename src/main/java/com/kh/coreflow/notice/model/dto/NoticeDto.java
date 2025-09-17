package com.kh.coreflow.notice.model.dto;

import java.util.Date;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.kh.coreflow.common.model.vo.FileDto.customFile;

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
	
	
	// 공지 등록(첨부파일)
	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	public static class NoticeInsert{
		private int notiId;
		private String title;
		private String content;
		private String essential;
		private String endDate;
		private String endTime;
		private List<String> depName;
		private List<String> posName;
		private List<MultipartFile> fileList;
	}
}
