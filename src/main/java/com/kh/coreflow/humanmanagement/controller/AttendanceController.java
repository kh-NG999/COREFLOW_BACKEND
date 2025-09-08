package com.kh.coreflow.humanmanagement.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kh.coreflow.humanmanagement.model.dto.AttendanceDto.AttendanceInfo;
import com.kh.coreflow.humanmanagement.model.service.AttendanceService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RestController
public class AttendanceController {
	private final AttendanceService service;
	
	// 사원 데이터 조회
	@CrossOrigin(origins="http://localhost:5173")
	@GetMapping("/attendance")
	public ResponseEntity<List<AttendanceInfo>> attInfo(
			@RequestParam(value="attDate", required=true) String attDateStr,
			@RequestParam(value="userNo", required=false) Integer userNo
			) {
		LocalDate attDate = LocalDate.parse(attDateStr,DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		Map<String,Object> params = new HashMap<>();
		params.put("attDate", attDate);
		params.put("userNo", userNo);
		
		List<AttendanceInfo> attInfoList = service.attInfo(params);
		
//		log.debug("attInfoList : {}",attInfoList);
//		System.out.println(attInfoList);
		
		if(attInfoList != null && !attInfoList.isEmpty()) {
			
			return ResponseEntity.ok(attInfoList); 
		}else {
			return ResponseEntity.noContent().build();
		}
	}
}
