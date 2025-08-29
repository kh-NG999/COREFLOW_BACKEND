package com.kh.coreflow.companypolicy.controller;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kh.coreflow.companypolicy.model.dto.CompanyPolicyDto.CompanyPolicy;
import com.kh.coreflow.companypolicy.model.service.CompanyPolicyService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins="http://localhost:5173", exposedHeaders="Location")
@RequestMapping("/cpolicies")
@Tag(name="Company Policy API", description="회사 규정 API")
public class CompanyPolicyController {
	private final CompanyPolicyService service;
	
	@GetMapping
	@Operation(summary="회사 규정 목록 조회.", description="회사 규정 목록 조회.")
	@ApiResponses({
		@ApiResponse(responseCode="200", description="조회 성공."),
		@ApiResponse(responseCode="404", description="조회 실패.")
	})
	public ResponseEntity<List<CompanyPolicy>> cpolicies() {
		List<CompanyPolicy> list = service.getPolicies();
		
		if (list.isEmpty()) {
			return ResponseEntity.notFound().build();
		} else {
			return ResponseEntity.ok(list);
		}
	}
	
	@PostMapping
	@Operation(summary="규정 추가.", description="입력한 회사 규정을 추가함.")
	@ApiResponses({
		@ApiResponse(responseCode="201", description="추가 성공."),
		@ApiResponse(responseCode="400", description="추가 실패.")
	})
	public ResponseEntity<Void> addPolicy(
			@RequestBody CompanyPolicy policy
			) {		
		int result = service.addPolicy(policy);
		
		if (result > 0) {
			URI location = URI.create("/cpolicies/" + policy.getPolicyId());
			return ResponseEntity.created(location).build();
		} else {
			return ResponseEntity.badRequest().build();
		}
	}
}









