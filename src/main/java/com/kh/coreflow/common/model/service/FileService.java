package com.kh.coreflow.common.model.service;

import org.springframework.web.multipart.MultipartFile;

public interface FileService {
	
	String saveFile(MultipartFile upfile, String boardCode);
	boolean deleteFile(String changeName, String boardCode);
}
