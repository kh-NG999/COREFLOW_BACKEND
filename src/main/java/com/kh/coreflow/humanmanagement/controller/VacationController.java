package com.kh.coreflow.humanmanagement.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kh.coreflow.humanmanagement.model.dto.VacationDto.MemberChoice;
import com.kh.coreflow.humanmanagement.model.dto.VacationDto.MemberVacation;
import com.kh.coreflow.humanmanagement.model.dto.VacationDto.VacationInfo;
import com.kh.coreflow.humanmanagement.model.service.VacationService;

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
		List<VacationInfo> vacInfoList = service.vacInfo(); 
//		log.debug("vacInfoList : {}",vacInfoList);
//		System.out.println(vacInfoList);
		
		if(vacInfoList != null && !vacInfoList.isEmpty()) {
			return ResponseEntity.ok(vacInfoList);			
		}else {
			return ResponseEntity.noContent().build();
		}
	}
	
	// 검색 사원 조회
	@CrossOrigin(origins="http://localhost:5173")
	@GetMapping("/vacation/member")
	public ResponseEntity<List<MemberChoice>> memberChoice(
			@RequestParam(value="userName", required=false) String userName
			){
		List<MemberChoice> memList = service.memChoice(userName);
//		log.debug("vacInfoList : {}",memList);
//		System.out.println(memList);
		
		if(memList != null && !memList.isEmpty()) {
			return ResponseEntity.ok(memList);
		}else {
			return ResponseEntity.noContent().build();
		}
	}
	
	// 사원 휴가 내역 조회
	@CrossOrigin(origins="http://localhost:5173")
	@GetMapping("/vacation/member/{userNo}")
	public ResponseEntity<List<MemberVacation>> MemberVacation(
			@PathVariable int userNo
			){
		List<MemberVacation> memVacation = service.memVacation(userNo);
//		log.debug("vacInfoList : {}",memVacation);
//		System.out.println(memVacation);
		
		if(memVacation != null && !memVacation.isEmpty()) {
			return ResponseEntity.ok(memVacation);
		}else {
			return ResponseEntity.noContent().build();
		}
	}
}