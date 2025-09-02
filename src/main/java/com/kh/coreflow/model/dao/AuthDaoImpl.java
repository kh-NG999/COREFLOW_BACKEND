package com.kh.coreflow.model.dao;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import com.kh.coreflow.model.dto.UserDto.User;
import com.kh.coreflow.model.dto.UserDto.UserAuthority;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class AuthDaoImpl implements AuthDao{
	
	private final SqlSessionTemplate session;
	
	@Override
	public Optional findUserByEmail(String email) {
		return session.selectOne("auth.findUserByEmail" , email);
	}

	@Override
	public void insertUser(User user) {
		session.insert("auth.insertUser",user);
	}

	@Override
	public void insertUserRole(UserAuthority auth) {
		session.insert("auth.insertUserRole",auth);
	}

	@Override
	public Optional<User> findUserByUserNo(int userNo) {
		return session.selectOne("auth.findUserByUserNo" , userNo);
	}

	@Override
	public User findUserPwd(String name, String email) {
		Map<String, String> param = new HashMap<>();
		param.put("name", name);
		param.put("email", email);
		return session.selectOne("auth.findUserPwd", param);
	}

	@Override
	public void updatePwd(String email, String encodedPwd) {
		Map<String, String> param = new HashMap<>();
		param.put("email", email);
		param.put("encodedPwd", encodedPwd);
		session.update("auth.updatePwd", param);
	}

	
	
}
