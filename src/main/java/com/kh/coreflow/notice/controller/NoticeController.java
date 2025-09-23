package com.kh.coreflow.notice.controller;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.kh.coreflow.notice.model.dto.NoticeDto.NoticeDetail;
import com.kh.coreflow.notice.model.dto.NoticeDto.NoticeInsert;
import com.kh.coreflow.notice.model.dto.NoticeDto.NoticeResponse;
import com.kh.coreflow.notice.model.service.NoticeService;
import com.kh.coreflow.common.model.service.FileService;
import com.kh.coreflow.common.model.vo.FileDto.customFile;
import com.kh.coreflow.model.dto.UserDto.UserDeptPoscode;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RestController
public class NoticeController {
	private final NoticeService service;
	private final FileService fileService;
	
	// 공지 조회 + 검색(제목, 내용, 작성자) 
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
		
		if(notiList != null && !notiList.isEmpty()) {
			return ResponseEntity.ok(notiList);
		}else {
			return ResponseEntity.noContent().build();
		}
	}

	// 공지 등록(첨부파일)
	@PostMapping("/notice/insert")
	public ResponseEntity<Void> noticeInsert(
			@AuthenticationPrincipal UserDeptPoscode auth,
			@RequestBody NoticeInsert insertParams
//			@RequestParam(value = "files", required=false) List<MultipartFile> file
			){
		//NOTICE 테이블에 저장하고 NOTI ID 갖고 오기
		insertParams.setUserNo(auth.getUserNo());
		
//		List<customFile> notiFile;
//		if(file.size()>0) {
//			notiFile = fileService.setOrChangeOneImage(file, insertParams.getNotiId(), "N");
//			log.info("notiFile : {}",notiFile);
//		}
		
		int result = service.notiInsert(insertParams);
		log.info("userNo : {}", auth.getUserNo());
		log.info("isertParams : {}", insertParams);
		
		if(result > 0) {
			return ResponseEntity.created(URI.create("/notice/insert")).build();
		}else {
			return ResponseEntity.badRequest().build();
		}
	}
	
	// 공지 상세 조회(첨부파일)
	@GetMapping("/notice/detail/{notiId}")
	public ResponseEntity<NoticeDetail> noticeDetail(
			@PathVariable int notiId
			){
		NoticeDetail notiDetail = service.notiDetail(notiId);
		
		log.info("notiDetail : {}",notiDetail);
		
		if(notiDetail != null) {
			return ResponseEntity.ok(notiDetail);
		}else {
			return ResponseEntity.notFound().build();
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
}