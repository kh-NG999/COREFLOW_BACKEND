package com.kh.coreflow.personal.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.kh.coreflow.model.dto.UserDto.User;
import com.kh.coreflow.model.dto.UserDto.UserUpdate;
import com.kh.coreflow.security.model.service.AuthService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{
	
	@Autowired
	private final AuthService authService;
	
	@Override
	public List<Object> getMySchedule(int userNo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updateMyInfo(UserUpdate userUpdate, MultipartFile profileImage) {
		int userNo = userUpdate.getUserNo();
		
		User user = authService.findUserByUserNo(userNo)
				.orElseThrow(() -> new RuntimeException("에러가 발생하였습니다"));
	}
	
	
	
}
