package com.kh.coreflow.approval.controller;

import java.net.URI;
import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.kh.coreflow.approval.model.dto.ApprovalDto;
import com.kh.coreflow.approval.model.dto.ApprovalFileDto;
import com.kh.coreflow.approval.model.dto.ApprovalLineDto;
import com.kh.coreflow.approval.model.service.ApprovalService;
import com.kh.coreflow.model.dto.UserDto.UserDeptcode;
import com.kh.coreflow.security.CustomUserDetails;
import com.kh.coreflow.security.model.service.AuthService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/approvals")
@CrossOrigin(origins = "*")
@Tag(name = "Approval API", description = "")
public class ApprovalController {
	
	private final ApprovalService service;
	private final AuthService authService;
	
	// 문서작성
	@Operation(summary = "결재문서 작성")
	@PostMapping(consumes={"multipart/form-data"})
    public ResponseEntity<?> submitApproval(
    		@RequestPart("approvalData") ApprovalDto approval,
    		@RequestPart(value = "files",required = false) MultipartFile file,
                          Principal principal) {
		
		if (approval.getApprovalTitle() == null || approval.getApprovalTitle().trim().isEmpty()) {
			return ResponseEntity.badRequest().body("제목은 필수입력 항목입니다");
		}
        int userNo = getUserNoFromPrincipal(principal); // DB에서 userNo 조회
        
        int approvalId = service.submitApproval(approval,file, userNo);
        
        return ResponseEntity.ok(approvalId);
    }

    // 결재 처리
    @Operation(summary = "결재 처리 (승인/반려)")
    @PostMapping("/{id}/process")
    public ResponseEntity<String> processApproval(@PathVariable("id") int approvalId,
                                                  Principal principal,
                                                  @RequestParam String action) {
    	try {
            int approverUserNo = getUserNoFromPrincipal(principal);
            service.processApproval(approvalId, approverUserNo, action);
            return ResponseEntity.ok("SUCCESS");
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // 문서 상세 조회
//    @Operation(summary = "결재문서 상세 조회")
//    @GetMapping("/{id}")
//    public ResponseEntity<ApprovalDto> getApproval(@PathVariable int id,
//    												Principal principal) {
//    	int userNo = getUserNoFromPrincipal(principal);
//        ApprovalDto approval = service.getApproval(id);
//        return ResponseEntity.ok(approval);
//    }
	
	// 현재 로그인 유저 대기중 문서조회
    @Operation(summary = "현재 로그인 유저의 대기 중 문서 목록 조회")
	@GetMapping("/pending")
	public ResponseEntity<List<ApprovalDto>> getPendingApprovals(Principal principal){
		int userNo = getUserNoFromPrincipal(principal);
		List<ApprovalDto> pending = service.getPendingApprovals(userNo);
		return ResponseEntity.ok(pending);
	}
	
	@Operation(summary = "모든 문서 목록 조회")
	@GetMapping("")
    public ResponseEntity<?> getDocuments(@AuthenticationPrincipal Object principal) {
        if (principal == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인 필요");
        }
        try {
            List<ApprovalDto> documents = service.getAllDocuments();
            return ResponseEntity.ok(documents);
        } catch (Exception e) {
            e.printStackTrace();
            Map<String, String> error = new HashMap<>();
            error.put("message", "문서 가져오기 중 서버 에러 발생");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    // Principal 객체에서 사용자 번호를 가져오는 유틸리티 메서드
    private int getUserNoFromPrincipal(Principal principal) {
    	Authentication authentication = (Authentication) principal;
    	
    	UserDeptcode userDetails = (UserDeptcode) authentication.getPrincipal();        
    	return userDetails.getUserNo();
    }
}




