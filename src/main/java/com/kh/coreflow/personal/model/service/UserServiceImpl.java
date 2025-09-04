package com.kh.coreflow.personal.model.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.kh.coreflow.model.dao.AuthDao;
import com.kh.coreflow.model.dto.UserDto.User;
import com.kh.coreflow.model.dto.UserDto.UserUpdate;
import com.kh.coreflow.security.model.service.AuthService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{
	
	@Autowired
	private final AuthService authService;
	private final AuthDao authDao;
	private final PasswordEncoder encoder;
	
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

	@Override
	public void updatePassword(int userNo, String userPwd) {
		Optional<User> user = authDao.findUserByUserNo(userNo);
		String email = user.get().getEmail();
		String encodedPwd = encoder.encode(userPwd);
		authDao.updatePwd(email, encodedPwd);
	}

	@Override
	public void updateUserPartial(int userNo, Map<String, Object> updates) {
		updates.put("userNo", userNo);
	    authDao.updateUserPartial(updates);
	}
	
	
	
}
