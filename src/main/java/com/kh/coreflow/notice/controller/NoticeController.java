package com.kh.coreflow.notice.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kh.coreflow.notice.model.dto.NoticeDto.NoticeResponse;
import com.kh.coreflow.notice.model.service.NoticeService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RestController
public class NoticeController {
	private final NoticeService service;
	
	// 공지 조회 + 검색(제목, 내용, 작성자) 
	@CrossOrigin(origins="http://localhost:5173")
	@GetMapping("/notice/main")
	public ResponseEntity<List<NoticeResponse>> notice(
			@RequestParam(value="searchType", defaultValue="title", required=false) String searchType,
			@RequestParam(value="keyword", required=false) String keyword
			){
		List<NoticeResponse> notiList;
		
		if(keyword != null && !keyword.trim().isEmpty()) {
			Map<String, String> params = new HashMap<>();
			params.put("searchType", searchType);
			params.put("keyword", keyword);
			notiList = service.notiList(params);
		}else {
			notiList = service.notiList();
		}
		
		log.info("notiList : {}",notiList);
		
		if(notiList != null && !notiList.isEmpty()) {
			return ResponseEntity.ok(notiList);
		}else {
			return ResponseEntity.noContent().build();
		}
	}

}
