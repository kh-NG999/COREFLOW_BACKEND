package com.kh.coreflow.personal.model.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
	
	@Autowired
	private final AuthDao authDao;
	private final PasswordEncoder encoder;
	
	@Override
	public List<Object> getMySchedule(Long userNo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updateMyInfo(UserUpdate userUpdate, MultipartFile profileImage) {
		Long userNo = userUpdate.getUserNo();
		
		User user = authService.findUserByUserNo(userNo)
				.orElseThrow(() -> new RuntimeException("에러가 발생하였습니다"));
	}

	@Override
	public void updatePassword(Long userNo, String userPwd, String string) {
		User user = authDao.findUserByUserNo(userNo)
                .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));
		String encodedPwd = encoder.encode(string);
		authDao.updatePwd(user.getEmail(), encodedPwd);
	}

	@Override
	public void updatePhone(Long userNo, String string) {
		authDao.updatePhone(userNo, string);
	}

	@Override
	public void updateAddress(Long userNo, String string, String string2) {
		authDao.updateAddress(userNo, string, string2);
	}
	
}
