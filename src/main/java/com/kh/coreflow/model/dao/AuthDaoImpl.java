package com.kh.coreflow.model.dao;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import com.kh.coreflow.model.dto.UserDto.User;
import com.kh.coreflow.model.dto.UserDto.UserAuthority;
import com.kh.coreflow.model.dto.UserDto.UserCredential;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class AuthDaoImpl implements AuthDao{
	
	private final SqlSessionTemplate session;
	
	@Override
	public User findUserByEmail(String email) {
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
	public User findUserByUserNo(int userNo) {
		return session.selectOne("auth.findUserByUserNo" , userNo);
	}

	
	
}
