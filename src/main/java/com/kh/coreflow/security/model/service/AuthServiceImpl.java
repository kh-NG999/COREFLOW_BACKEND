package com.kh.coreflow.security.model.service;

import java.util.List;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kh.coreflow.model.dao.AuthDao;
import com.kh.coreflow.model.dto.UserDto.AuthResult;
import com.kh.coreflow.model.dto.UserDto.User;
import com.kh.coreflow.model.dto.UserDto.UserAuthority;
import com.kh.coreflow.security.model.provider.JWTProvider;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService{
	
	private final AuthDao authDao;
	private final PasswordEncoder encoder;
	private final JWTProvider jwt;
	
	@Override
	public boolean existsByEmail(String email) {
		User user = authDao.findUserByEmail(email);
		return user != null;
	}

	@Override
	public AuthResult login(String email, String userPwd) {
		// 1. 사용자 정보 조회
				User user = authDao.findUserByEmail(email);
				
				if(!encoder.matches(userPwd, user.getUserPwd())) {
					throw new BadCredentialsException("비밀번호 오류");
				}
				
				// 2. 토큰 발급
				String accessToken = jwt.createAccessToken(user.getUserNo(), 30);
				String refreshToken = jwt.createRefreshToken(user.getUserNo(), 7);
				
				User userNoPassword = User.builder()
										.userNo(user.getUserNo())
										.email(user.getEmail())
										.name(user.getName())
										.profile(user.getProfile())
										.roles(user.getRoles())
										.build();
				
				return AuthResult.builder()
						.accessToken(accessToken)
						.refreshToken(refreshToken)
						.user(userNoPassword)
						.build();
	}

	@Override
	//@Transactional
	public AuthResult signUp(String email, String userPwd) {
		log.info("pwd : {}",userPwd);
		// 1) Users테이블에 데이터 추가
				User user = User.builder()
						.email(email)
						.name(email.split("@")[0])
						.userPwd(encoder.encode(userPwd))
						.build();
				authDao.insertUser(user);
				
				// 3) 권한추가
				UserAuthority auth = UserAuthority.builder()
													.userNo(user.getUserNo())
													.roles(List.of("ROLE_USER"))
													.build();
				authDao.insertUserRole(auth);
				
				//토큰 발급
				String accessToken = jwt.createAccessToken(user.getUserNo(), 30); // 30분
				String refreshToken = jwt.createRefreshToken(user.getUserNo(), 7); // 7일
				
				user = authDao.findUserByUserNo(user.getUserNo()); // 비밀번호 제외 필요
				
				return AuthResult.builder()
						.accessToken(accessToken)
						.refreshToken(refreshToken)
						.user(user)
						.build();
	}

	@Override
	public AuthResult refreshByCookie(String refreshCookie) {
		int userNo = jwt.parseRefresh(refreshCookie);
		User user = authDao.findUserByUserNo(userNo);
		
		String accessToken = jwt.createAccessToken(userNo, 30);
		
		return AuthResult.builder()
				.accessToken(accessToken)
				.user(user)
				.build();
	}

	@Override
	public User findUserByUserNo(int userNo) {
		return authDao.findUserByUserNo(userNo);
	}
	
	

}
