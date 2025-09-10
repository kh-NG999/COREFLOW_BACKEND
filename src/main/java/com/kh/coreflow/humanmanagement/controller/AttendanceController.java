package com.kh.coreflow.humanmanagement.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kh.coreflow.humanmanagement.model.dto.AttendanceDto.AttendanceInfo;
import com.kh.coreflow.humanmanagement.model.service.AttendanceService;
import com.kh.coreflow.model.dto.UserDto.UserDeptcode;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RestController
public class AttendanceController {
	private final AttendanceService service;
	
	// 전체 사원 근태 정보 조회
	@CrossOrigin(origins="http://localhost:5173")
	@GetMapping("/attendance/member")
	public ResponseEntity<List<AttendanceInfo>> allAttendance(
			@RequestParam(value="attDate", required=true) String attDateStr,
			@RequestParam(value="userNo", required=false) Integer userNo
			) {
		LocalDate attDate = LocalDate.parse(attDateStr,DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		Map<String,Object> params = new HashMap<>();
		params.put("attDate", attDate);
		params.put("userNo", userNo);
		
		List<AttendanceInfo> memAttendance = service.memAttendance(params);
				
		if(memAttendance != null && !memAttendance.isEmpty()) {
			
			return ResponseEntity.ok(memAttendance); 
		}else {
			return ResponseEntity.noContent().build();
		}
	}
	
	// 로그인 사용자 근태 정보 조회
	@CrossOrigin(origins="http://localhost:5173")
	@GetMapping("/attendance/personal")
	public ResponseEntity<List<AttendanceInfo>> personalAttendance(
			Authentication auth,
			@RequestParam int year,
			@RequestParam int month
			){
		long userNo = ((UserDeptcode)auth.getPrincipal()).getUserNo();
		
		log.info("userNo : {} / year : {} / month : {}",userNo,year,month);
		
		Map<String,Object> params = new HashMap<>();
		params.put("userNo", userNo);
		params.put("year", year);
		params.put("month", month);
		
		List<AttendanceInfo> perAttendance = service.perAttendance(params);
		
		if(perAttendance != null && !perAttendance.isEmpty()) {
			return ResponseEntity.ok(perAttendance);
		}else {
			return ResponseEntity.noContent().build();
		}
	}
	
	// 버튼 클릭시 
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
