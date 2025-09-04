package com.kh.coreflow.humanmanagement.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.kh.coreflow.humanmanagement.model.dto.VacationDto.VacationInfo;
import com.kh.coreflow.humanmanagement.model.service.VacationService;
import com.kh.coreflow.humanmanagement.model.service.VacationServiceImpl;
import com.nimbusds.oauth2.sdk.Response;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RestController
public class VacationController {
	private final VacationService service;
	
	// 연차 정보 조회
	@CrossOrigin(origins="http://localhost:5173")
	@GetMapping("/vacation/info")
	public ResponseEntity<List<VacationInfo>> vacInfo(){
		List<VacationInfo> vacInfoList = service.vacationInfo(); 
		log.debug("vacInfoList : {}",vacInfoList);
		System.out.println(vacInfoList);
		
		if(vacInfoList != null && !vacInfoList.isEmpty()) {
			return ResponseEntity.ok(vacInfoList);			
		}else {
			return ResponseEntity.noContent().build();
		}
	}
	
	// 연차 정보 수정
	@CrossOrigin(origins="http://localhost:5173")
	@PatchMapping("/vacation/info")
	public ResponseEntity<Void> vacInfoUpdate(
			@RequestBody VacationInfo vacInfo
			){
		int result = service.vacInfoUpdate(vacInfo);
		
		if(result > 0) {
			return ResponseEntity.noContent().build();
		}else {
			return ResponseEntity.notFound().build();
			
		}
	}

}
