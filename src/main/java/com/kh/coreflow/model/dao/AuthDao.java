package com.kh.coreflow.model.dao;

import java.util.Map;
import java.util.Optional;

import com.kh.coreflow.model.dto.UserDto.User;
import com.kh.coreflow.model.dto.UserDto.UserAuthority;

public interface AuthDao {

	Optional<User> findUserByEmail(String email);

	void insertUser(User user);

	void insertUserRole(UserAuthority auth);

	Optional<User> findUserByUserNo(Long userNo);

	User findUserPwd(String userName, String email);

	void updatePwd(String email, String encodedPwd);

	void updatePhone(Long userNo, String string);

	void updateAddress(Long userNo, String string);

	void updateProfileImage(Map<String, Object> imageUpdate);

	int checkProfileImage(Long userNo);
	
	void insertProfileImage(Map<String, Object> imageUpdate);

	UserAuthority findUserAuthorityByUserNo(Long userNo);


	
}
