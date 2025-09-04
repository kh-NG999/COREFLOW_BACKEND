package com.kh.coreflow.security.controller;

import java.time.Duration;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kh.coreflow.model.dto.UserDto.AuthResult;
import com.kh.coreflow.model.dto.UserDto.LoginRequest;
import com.kh.coreflow.model.dto.UserDto.User;
import com.kh.coreflow.security.model.provider.JWTProvider;
import com.kh.coreflow.security.model.service.AuthService;
import com.kh.coreflow.validator.UserValidator;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {

	private final AuthService service;
	private final JWTProvider jwt;
	public static final String REFRESH_COOKIE = "REFRESH_TOKEN";
	
	@PostMapping("/login")
	public ResponseEntity<AuthResult> login(@RequestBody LoginRequest req){
		
		//1) 사용자가 존재하느지 확인
		boolean exists = service.existsByEmail(req.getEmail());
		
		if(!exists) {
			return ResponseEntity.notFound().build();
		}
		try {
			AuthResult result = service.login(req.getEmail(), req.getPassword());
			
			// refreshToken은 http-only쿠키로 설정하여 반환
			ResponseCookie refreshCookie = 
					ResponseCookie
					.from(REFRESH_COOKIE, result.getRefreshToken())
					.httpOnly(true)
					.secure(false) // https에서만 사용하는지 여부
					.path("/")
					.sameSite("Lax")
					.maxAge(Duration.ofDays(7)) // 만료시간
					.build();
			
			return ResponseEntity.ok()
					.header(HttpHeaders.SET_COOKIE, refreshCookie.toString())
					.body(result);
			
		}catch (BadCredentialsException e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build(); //401
		}	
	}
	/*
	 * 회원가입
	 */
	@PostMapping("/signup")
	public ResponseEntity<AuthResult> signup(@RequestBody LoginRequest req){
		AuthResult result = service.signUp(req.getEmail(), req.getPassword());
		
		// refreshToken은 http-only쿠키로 설정하여 반환
		ResponseCookie refreshCookie = 
				ResponseCookie
				.from(REFRESH_COOKIE, result.getRefreshToken())
				.httpOnly(true)
				.secure(false) // https에서만 사용하는지 여부
				.path("/")
				.sameSite("Lax")
				.maxAge(Duration.ofDays(7)) // 만료시간
				.build();
		
		return ResponseEntity.ok()
				.header(HttpHeaders.SET_COOKIE, refreshCookie.toString())
				.body(result);
	}
	
	@PostMapping("/refresh")
	public ResponseEntity<AuthResult> refresh(
			@CookieValue(name = REFRESH_COOKIE, required = false)
			String refreshCookie
			){
		if(refreshCookie == null || refreshCookie.isBlank()) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
		
		//쿠키가 있으면 쿠키를 검증하여 새로운 accessToken생성
		AuthResult result = service.refreshByCookie(refreshCookie);
		return ResponseEntity.ok(result);
	}
	
	@PostMapping("/logout")
	public ResponseEntity<Void> logout(HttpServletRequest request){
		
		// 1. 클라이언트 헤더에서 id값 추출
		String accessToken = resolveAccessToken(request);
		int userId = jwt.getUserNo(accessToken);
				
		// 리프레쉬토큰 제거
		ResponseCookie refreshCookie = 
				ResponseCookie
				.from(REFRESH_COOKIE, "")
				.httpOnly(true)
				.secure(false) // https에서만 사용하는지 여부
				.path("/")
				.sameSite("Lax")
				.maxAge(0) // 만료시간
				.build();
		return ResponseEntity.noContent().header(HttpHeaders.SET_COOKIE, refreshCookie.toString()).build();
	}
	
	@GetMapping("/me")
	public ResponseEntity<User> getUserInfo(HttpServletRequest req){
		
		// 1. 요청 헤더에서 jwt토큰 추출
		String jwtToken = resolveAccessToken(req);
		if(jwtToken == null) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
		
		// 2. JWT토큰에서 ID값 추출하기
		int userNo = jwt.getUserNo(jwtToken);
		
		// 사용자 정보 조회
		User user = service.findUserByUserNo(userNo);
		if(user == null) {
			return ResponseEntity.notFound().build();
		}
		
		return ResponseEntity.ok(user);
	}

	private String resolveAccessToken(HttpServletRequest request) {
		String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
	}
}













