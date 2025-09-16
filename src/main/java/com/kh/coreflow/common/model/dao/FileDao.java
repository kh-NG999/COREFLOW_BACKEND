package com.kh.coreflow.common.model.dao;

import com.kh.coreflow.common.model.vo.FileDto.customFile;

public interface FileDao {

	customFile getFile(String imageCode, Long userNo);

	int changeOneImage(customFile profileImage);

	int insertOneImage(customFile profileImage);

}
