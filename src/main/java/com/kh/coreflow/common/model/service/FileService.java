package com.kh.coreflow.common.model.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.kh.coreflow.common.model.vo.FileDto.customFile;

public interface FileService {
	
	String saveFile(MultipartFile upfile, String imageCode);
	
	boolean deleteFile(String changeName, String imageCode);

	customFile getFile(String imageCode, Long refId);
	
	List<customFile> getFiles(String imageCode, Long refId);

	customFile setOrChangeOneImage(MultipartFile file, Long refId, String fileCode);

	customFile findFile(String imageCode, String changeName);
	
	List<customFile> setOrChangeImage(List<MultipartFile> files,Long refId, String fileCode);
}
