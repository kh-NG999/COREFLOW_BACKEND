package com.kh.coreflow.model.dao;

import com.kh.coreflow.model.dto.UserDto.User;
import com.kh.coreflow.model.dto.UserDto.UserAuthority;
import com.kh.coreflow.model.dto.UserDto.UserCredential;

public interface AuthDao {

	User findUserByEmail(String email);

	void insertUser(User user);

	void insertUserRole(UserAuthority auth);

	User findUserByUserNo(int userNo);

	User findUserPwd(String name, String email);

	void updatePwd(String email, String encodedPwd);
	
	
}
