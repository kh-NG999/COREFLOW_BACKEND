package com.kh.coreflow.approval.model.dto;

import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class ApprovalDto {	
	
	private int approvalId;
    private int userNo; // 작성자 ID
    private String approvalTitle;
    private String approvalDetail;
    private String approvalType;
    private int approvalStatus; // 0:임시저장, 1:진행, 2:승인, 3:반려
    private Date startDate;
    private Date endDate;
    private Date saveDate;		// 임시저장날짜
    
    private List<Integer> approverUserNo;
    private List<Integer> ccUserNo;
	
	
	private List<ApprovalLineDto> lines;
	
	private List<ApprovalFileDto> files;


	

	
	
}













