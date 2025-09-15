package com.kh.coreflow.common.model.service;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class FileServiceImpl implements FileService {
	@Value("${file.upload-dir}")
    private String uploadDir;

	@Override
	public String saveFile(MultipartFile upfile, String boardCode) {
	    String serverFolderPath = uploadDir + boardCode + "/";
	    File dir = new File(serverFolderPath);
	    if(!dir.exists()) {
	        dir.mkdirs();
	    }
	    
	    String originName = upfile.getOriginalFilename();
	    String currentTime = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
	    int random = (int)(Math.random() *90000 + 10000);
	    
	    String ext = "";
	    if (originName != null && originName.contains(".")) {
	        ext = originName.substring(originName.lastIndexOf("."));
	    }
	    
	    String changeName = currentTime + random + ext;
	    
	    try {
	        upfile.transferTo(new File(serverFolderPath + changeName));
	    } catch (IOException e) {
	        log.error("파일 저장 실패: {}", e.getMessage());
	        return null; // 또는 실패 시 null 반환
	    }
	    
	    return changeName; 
	}
	
	@Override
	public boolean deleteFile(String changeName, String boardCode) {
	    // 1. 파일명이 유효한지 확인합니다.
	    if (changeName == null || changeName.isEmpty()) {
	        log.warn("삭제할 파일명이 없습니다.");
	        return false;
	    }
	    String fullPath = Paths.get(uploadDir, boardCode, changeName).toString();
	    File fileToDelete = new File(fullPath);

	    // 3. 파일이 실제로 존재하는지 확인하고 삭제를 시도합니다.
	    if (fileToDelete.exists()) {
	        if (fileToDelete.delete()) {
	            log.info("파일 삭제 성공: {}", fullPath);
	            return true; // 파일 삭제 성공
	        } else {
	            log.error("파일 삭제 실패: {}", fullPath);
	            return false; // 파일 삭제 실패
	        }
	    } else {
	        log.warn("삭제할 파일이 존재하지 않습니다: {}", fullPath);
	        return false; // 파일이 존재하지 않음
	    }
	}
}
