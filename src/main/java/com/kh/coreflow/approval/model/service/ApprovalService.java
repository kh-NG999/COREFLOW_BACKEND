package com.kh.coreflow.approval.model.service;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kh.coreflow.approval.model.dao.ApprovalDao;
import com.kh.coreflow.approval.model.dto.ApprovalDto;
import com.kh.coreflow.approval.model.dto.ApprovalFileDto;
import com.kh.coreflow.approval.model.dto.ApprovalLineDto;

@Service
public class ApprovalService {

    private final ApprovalDao dao;

    public ApprovalService(ApprovalDao dao) {
        this.dao = dao;
    }

    @Transactional
    public int submitApproval(ApprovalDto approval, int userNo) {
        approval.setUserNo(userNo);
        approval.setApprovalStatus(1);
        approval.setSaveDate(new Date());
        dao.insertApproval(approval);

        if (approval.getLines() != null) {
            int order = 1;
            for (ApprovalLineDto line : approval.getLines()) {
                line.setApprovalId(approval.getApprovalId());
                line.setLineOrder(order++);
                line.setLineStatus(line.getLineOrder() == 1 ? "WAITING" : "PENDING");
                dao.insertApprovalLine(line);
            }
        }

        if (approval.getFiles() != null) {
            for (ApprovalFileDto file : approval.getFiles()) {
                file.setApprovalId(approval.getApprovalId());
                dao.insertApprovalFile(file);
            }
        }

        return approval.getApprovalId();
    }

    @Transactional
    public void processApproval(int approvalId, int approverUserId, String action) {
        List<ApprovalLineDto> waiting = dao.findWaitingLinesByApprover(approvalId, approverUserId);
        if (waiting == null || waiting.isEmpty()) {
            throw new IllegalStateException("현재 결재자가 대기 상태가 아닙니다");
        }

        ApprovalLineDto currentLine = waiting.get(0);

        if ("APPROVE".equalsIgnoreCase(action)) {
            dao.updateApprovalLineStatus(currentLine.getLineId(), "APPROVED");
            ApprovalLineDto nextLine = dao.findLineByApprovalIdAndOrder(approvalId, currentLine.getLineOrder() + 1);
            if (nextLine != null) {
                dao.updateApprovalLineStatus(nextLine.getLineId(), "WAITING");
            } else {
                ApprovalDto approval = new ApprovalDto();
                approval.setApprovalId(approvalId);
                approval.setApprovalStatus(2);
                dao.updateApprovalStatus(approval);
            }
        } else if ("REJECT".equalsIgnoreCase(action)) {
            dao.updateApprovalLineStatus(currentLine.getLineId(), "REJECTED");
            ApprovalDto approval = new ApprovalDto();
                approval.setApprovalId(approvalId);
                approval.setApprovalStatus(3);
                dao.updateApprovalStatus(approval);
        } else {
            throw new IllegalArgumentException("action은 APPROVE 또는 REJECT만 가능합니다");
        }
    }

    public ApprovalDto getApproval(int approvalId) {
        ApprovalDto approval = dao.findById(approvalId);
        approval.setLines(dao.findLinesByApprovalId(approvalId));
        approval.setFiles(dao.findFilesByApprovalId(approvalId));
        return approval;
    }

    public List<ApprovalDto> getAllDocuments() {
        return dao.selectAllApprovals();
    }
    
    public List<ApprovalDto> getPendingApprovals(int userId) {
        return dao.findPendingApprovals(userId);
    }
    
    public List<ApprovalLineDto> getApprovalLines(int approvalId){
    	return dao.findLinesByApprovalId(approvalId);
    }
    
    public List<ApprovalFileDto> getApprovalFiles(int approvalId){
    	return dao.findFilesByApprovalId(approvalId);
    }
    
    
    
    
    
    
    
}