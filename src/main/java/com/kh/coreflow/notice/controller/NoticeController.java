package com.kh.coreflow.notice.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.kh.coreflow.notice.model.dto.NoticeDto.NoticeInsert;
import com.kh.coreflow.notice.model.dto.NoticeDto.NoticeResponse;
import com.kh.coreflow.notice.model.service.NoticeService;
import com.kh.coreflow.common.model.service.FileService;
import com.kh.coreflow.common.model.vo.FileDto.customFile;
import com.kh.coreflow.model.dto.UserDto.UserDeptcode;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RestController
public class NoticeController {
	private final NoticeService service;
	private final FileService fileService;
	
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

	// 공지 등록(첨부파일)
	@CrossOrigin(origins="http://localhost5173")
	@PostMapping("/notice/insert")
	public ResponseEntity<Void> noticeInsert(
			@AuthenticationPrincipal UserDeptcode auth,
			@RequestParam NoticeInsert insertParams,
			@RequestParam("files") List<MultipartFile> file
			){
		//NOTICE 테이블에 저장하고 NOTI ID 갖고 오기
		log.info("userNo : {}", auth.getUserNo());
		log.info("isertParams : {}", insertParams);
		log.info("file : {}", file);
		
//		List<customFile> notiFile;
//		if(file.size()>0) {
//			notiFile = fileService.setOrChangeOneImage(file, insertParams.getNotiId(), "N");
//			log.info("notiFile : {}",notiFile);
//		}
		
		Map<String, Object> params = new HashMap<>();
		params.put("userNo", auth.getUserNo());
		params.put("isertParams", insertParams);
		
		int result = service.notiInsert(params);
		
		if(result > 0) {
			return ResponseEntity.ok(null);
		}else {
			return ResponseEntity.badRequest().build();
		}
		
	}
}
