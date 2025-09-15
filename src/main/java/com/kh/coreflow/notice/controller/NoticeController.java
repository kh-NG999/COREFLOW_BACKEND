package com.kh.coreflow.notice.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
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
	
	// 전체 공지 조회
	@CrossOrigin(origins="http://localhost:5173")
	@GetMapping("/notice/main")
	public ResponseEntity<List<NoticeResponse>> notice(){
		List<NoticeResponse> notiList = service.notiList();
		log.info("notiList : {}",notiList);
		
		if(notiList != null && !notiList.isEmpty()) {
			return ResponseEntity.ok(notiList);
		}else {
			return ResponseEntity.noContent().build();
		}
		
	}
	
	
}
