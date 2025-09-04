package com.kh.coreflow.personal.model.service;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.kh.coreflow.model.dto.UserDto.UserUpdate;

public interface UserService {

	List<Object> getMySchedule(int userNo);

	void updateMyInfo(UserUpdate userUpdate, MultipartFile profile);

	void updatePassword(int userNo, String userPwd);

	void updateUserPartial(int userNo, Map<String, Object> updates);

}
