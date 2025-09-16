package com.kh.coreflow.common.model.service;

import org.springframework.web.multipart.MultipartFile;

import com.kh.coreflow.common.model.vo.FileDto.customFile;

public interface FileService {
	
	String saveFile(MultipartFile upfile, String imageCode);
	
	boolean deleteFile(String changeName, String imageCode);
	
	customFile getFile(String imageCode, Long userNo);

	customFile setOrChangeOneImage(MultipartFile file, Long userNo, String fileCode);
}
