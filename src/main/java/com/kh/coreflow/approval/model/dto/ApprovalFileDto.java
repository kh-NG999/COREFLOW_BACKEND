package com.kh.coreflow.approval.model.dto;

import lombok.Data;

//파일첨부
@Data
public class ApprovalFileDto {
	private int fileId;
    private long approvalId;
    private String originName;
    private String changeName;
    private String filePath;
}
