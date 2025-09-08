package com.kh.coreflow.personal.model.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.kh.coreflow.model.dto.UserDto.UserUpdate;

public interface UserService {

	List<Object> getMySchedule(int userNo);

	void updateMyInfo(UserUpdate userUpdate, MultipartFile profile);

	void updatePassword(int userNo, String userPwd, String string);

	void updatePhone(int userNo, String string);

	void updateAddress(int userNo, String string);

	String updateProfileImage(int userNo, MultipartFile file);
	
	
}
