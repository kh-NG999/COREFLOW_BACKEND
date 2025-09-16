package com.kh.coreflow.approval.model.service;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.kh.coreflow.approval.model.dao.ApprovalDao;
import com.kh.coreflow.approval.model.dto.ApprovalDto;
import com.kh.coreflow.approval.model.dto.ApprovalFileDto;
import com.kh.coreflow.approval.model.dto.ApprovalLineDto;

@Service
public class ApprovalService {

    private final ApprovalDao dao;
    @Value("${file.upload-dir}")
    private String uploadPath;

    public ApprovalService(ApprovalDao dao) {
        this.dao = dao;
    }

    @Transactional
    public int submitApproval(ApprovalDto approval,MultipartFile file, int userNo) {
        approval.setUserNo(userNo);
//        approval.setApprovalStatus(1);
        approval.setSaveDate(new Date());
        dao.insertApproval(approval);

        // 결재선 정보
        if (approval.getApproverUserNo() != null) {
            for (Integer approverNo : approval.getApproverUserNo()) {
            	ApprovalLineDto line = new ApprovalLineDto();
                line.setApprovalId(approval.getApprovalId());
                line.setApproverUserNo(approverNo);
                line.setLineOrder(1);
                line.setLineStatus("WAITING");
                dao.insertApprovalLine(line);
            }
        }
        // 참조자 저장
        if (approval.getCcUserNo() != null) {
        	for (Integer ccNo : approval.getCcUserNo()) {
        		ApprovalLineDto line = new ApprovalLineDto();
        		line.setApprovalId(approval.getApprovalId());
        		line.setApproverUserNo(ccNo);
        		line.setLineOrder(2);
        		line.setLineStatus("PENDING");
        		dao.insertApprovalLine(line);
        	}
        }

        // 파일정보
        if (file != null && !file.isEmpty()) {
            try {
            	// 파일객체 생성
            	File uploadDir = new File(uploadPath);
            	
            	//폴더없으면 폴더생성
            	if (!uploadDir.exists()) {
            		uploadDir.mkdir(); 
            	}
            	String uploadPath = "C:/uploads/";
            	
            	String originalFileName = file.getOriginalFilename();
            	String ext = originalFileName.substring(originalFileName.lastIndexOf("."));
            	String uniqueFileName = UUID.randomUUID().toString() + ext;
            	
            	File saveFile = new File(uploadPath + uniqueFileName);
            	file.transferTo(saveFile);
            	
            	ApprovalFileDto fileDto = new ApprovalFileDto();
            	fileDto.setApprovalId(approval.getApprovalId());
            	fileDto.setOriginalFileName(originalFileName);
            	fileDto.setFilePath(uploadPath+ uniqueFileName);
            	fileDto.setFileSize(file.getSize());
            	
            	dao.insertApprovalFile(fileDto);
            } catch (IOException e) {
            	e.printStackTrace();
            	throw new RuntimeException("파일 저장 실패");
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

//    public ApprovalDto getApproval(int approvalId) {
//        ApprovalDto approval = dao.findById(approvalId);
//        approval.setLines(dao.findLinesByApprovalId(approvalId));
//        approval.setFiles(dao.findFilesByApprovalId(approvalId));
//        return approval;
//    }

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

	public List<ApprovalDto> getDocumentsByUser(int userNo) {
		return dao.selectApprovalsByUserNo(userNo);
	}

	public ApprovalDto getApprovalDetails(int approvalId) {
		
		ApprovalDto approval = dao.findById(approvalId);
		
		if(approval != null) {
			approval.setLines(dao.findLinesByApprovalId(approvalId));
			approval.setFiles(dao.findFilesByApprovalId(approvalId));
		}
		return approval;
		
	}

	public List<ApprovalDto> getReceivedDocumentsByUser(int userNo) {
		return dao.selectReceivedApprovalsByApproverNo(userNo);
	}

	public void updateApprovalStatus(int approvalId, Integer newStatus) {
		
		ApprovalDto approvalToUpdate = new ApprovalDto();
		
		approvalToUpdate.setApprovalId(approvalId);
		approvalToUpdate.setApprovalStatus(newStatus);
		
		int result = dao.updateApprovalStatus(approvalToUpdate);
		
		if (result == 0) {
			throw new RuntimeException("문서 상태변경 실패 (ID: \" + approvalId + \")");
		}
	}
	
	public List<ApprovalDto> getProcessedDocumentsByUser(int userNo){
		return dao.selectProcessedApprovalsByApproverNo(userNo);
	}
	
	public List<ApprovalDto> getTempDocumentsByUser(int userNo){
		return dao.selectTempApprovalsByUserNo(userNo);
	}

	@Transactional
	public void updateApproval(ApprovalDto approval, MultipartFile file, int userNo) {
		approval.setUserNo(userNo);
		dao.updateApproval(approval);
		
		dao.deleteApprovalLines(approval.getApprovalId());
		
		insertLinesAndFiles(approval, file);
	}

	private void insertLinesAndFiles(ApprovalDto approval, MultipartFile file) {
        // 결재선 정보
        if (approval.getApproverUserNo() != null) {
            for (Integer approverNo : approval.getApproverUserNo()) {
                ApprovalLineDto line = new ApprovalLineDto();
                line.setApprovalId(approval.getApprovalId());
                line.setApproverUserNo(approverNo);
                line.setLineOrder(1);
                line.setLineStatus("WAITING");
                dao.insertApprovalLine(line);
            }
        }
        // 참조자 저장
        if (approval.getCcUserNo() != null) {
            for (Integer ccNo : approval.getCcUserNo()) {
                ApprovalLineDto line = new ApprovalLineDto();
                line.setApprovalId(approval.getApprovalId());
                line.setApproverUserNo(ccNo);
                line.setLineOrder(2);
                line.setLineStatus("PENDING");
                dao.insertApprovalLine(line);
            }
        }
        if (file != null && !file.isEmpty()) {
        }
    }
	

}

	
    
    
    
    
    
    
    
