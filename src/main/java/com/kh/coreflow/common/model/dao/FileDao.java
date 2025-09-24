package com.kh.coreflow.common.model.dao;

import java.util.List;

import com.kh.coreflow.common.model.vo.FileDto.customFile;

public interface FileDao {

	customFile getFile(String imageCode, Long refId);

	List<customFile> getFiles(String imageCode, Long refId);

	int changeOneImage(customFile profileImage);

	int insertOneImage(customFile profileImage);

	customFile findFile(String fileCode, String changeName);
	
	int removeFile(String fileCode, Long refId);

}
