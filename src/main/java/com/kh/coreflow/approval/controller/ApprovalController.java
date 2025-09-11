package com.kh.coreflow.approval.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.kh.coreflow.approval.model.dto.ApprovalDto;
import com.kh.coreflow.approval.model.service.ApprovalService;
import com.kh.coreflow.model.dto.UserDto.UserDeptcode;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

// 결재 처리 요청을 받기 위한 DTO
@Data
class ApprovalRequest {
    private String action; // "APPROVE" 또는 "REJECT"
}

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/approvals")
@Tag(name = "Approval API", description = "")
public class ApprovalController {

    private final ApprovalService service;
    
    // --- 유틸리티 메소드 (클래스 하단에서 위로 이동하여 가독성 향상) ---
    private int getUserNoFromPrincipal(Principal principal) {
        Authentication authentication = (Authentication) principal;
        UserDeptcode userDetails = (UserDeptcode) authentication.getPrincipal();
        return userDetails.getUserNo();
    }

    // --- API 메소드 ---

    @Operation(summary = "결재문서 작성")
    @PostMapping(consumes = {"multipart/form-data"})
    public ResponseEntity<?> submitApproval(
            @RequestPart("approvalData") ApprovalDto approval,
            @RequestPart(value = "files", required = false) MultipartFile file,
            Principal principal) {
        
        if (approval.getApprovalTitle() == null || approval.getApprovalTitle().trim().isEmpty()) {
            return ResponseEntity.badRequest().body("제목은 필수입력 항목입니다");
        }
        int userNo = getUserNoFromPrincipal(principal);
        int approvalId = service.submitApproval(approval, file, userNo);
        return ResponseEntity.ok(approvalId);
    }

    @Operation(summary = "결재 처리 (승인/부결)")
    @PostMapping("/{approvalId}/process")
    public ResponseEntity<String> processApproval(
            @PathVariable("approvalId") int approvalId,
            @RequestBody ApprovalRequest request,
            Principal principal) {

        try {
            // 클래스 내 유틸리티 메소드를 사용하여 사용자 번호 조회
            int approverUserId = getUserNoFromPrincipal(principal);

            // 주입받은 service 객체의 메소드를 호출
            service.processApproval(approvalId, approverUserId, request.getAction());

            return ResponseEntity.ok("결재가 성공적으로 처리되었습니다.");
        } catch (IllegalStateException | IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            log.error("결재 처리 중 오류 발생", e); // 서버 로그에 에러 기록
            return ResponseEntity.status(500).body("결재 처리 중 서버 오류가 발생했습니다.");
        }
    }

    @Operation(summary = "현재 로그인 유저의 대기 중 문서 목록 조회")
    @GetMapping("/pending")
    public ResponseEntity<List<ApprovalDto>> getPendingApprovals(Principal principal) {
        int userNo = getUserNoFromPrincipal(principal);
        List<ApprovalDto> pending = service.getPendingApprovals(userNo);
        return ResponseEntity.ok(pending);
    }

    @Operation(summary = "내문서함 문서 조회")
    @GetMapping("/my-documents")
    public ResponseEntity<List<ApprovalDto>> getDocuments(Principal principal) {
        int userNo = getUserNoFromPrincipal(principal);
        List<ApprovalDto> documents = service.getDocumentsByUser(userNo);
        return ResponseEntity.ok(documents);
    }

    @Operation(summary = "결재문서 상세 조회")
    @GetMapping("/{id}")
    public ResponseEntity<ApprovalDto> getApprovalDetails(@PathVariable("id") int approvalId) {
        ApprovalDto approval = service.getApprovalDetails(approvalId);
        if (approval == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(approval);
    }

    @Operation(summary = "받은문서함")
    @GetMapping("/received-documents")
    public ResponseEntity<List<ApprovalDto>> getReceivedDocuments(Principal principal) {
        int userNo = getUserNoFromPrincipal(principal);
        List<ApprovalDto> documents = service.getReceivedDocumentsByUser(userNo);
        return ResponseEntity.ok(documents);
    }
}