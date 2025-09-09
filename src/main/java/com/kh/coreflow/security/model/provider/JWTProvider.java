package com.kh.coreflow.security.model.provider;

import java.security.Key;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JWTProvider {

	private final Key key;
	private final Key refreshKey;
	
	public JWTProvider(
			@Value("${jwt.secret}")
			String secretBase64,
			@Value("${jwt.refresh-secret}")
			String refreshSecretBase64
			) {
		byte[] keyBytes = Decoders.BASE64.decode(secretBase64);
		this.key = Keys.hmacShaKeyFor(keyBytes);
		this.refreshKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(refreshSecretBase64));
	}

	public String createAccessToken(Long userNo, Long depId, List<String> roles, int minutes) {
		Date now = new Date();
		return Jwts.builder()
				.setSubject(String.valueOf(userNo)) // 페이로드에 저장할 id
				.claim("roles", roles)
				.claim("depId", depId)
				.setIssuedAt(now) // 토큰 발행시간
				.setExpiration(new Date(now.getTime()+(1000L * 60 * minutes)))
				.signWith(key, SignatureAlgorithm.HS256) // 서명에 사용할 키값과, 알고리즘
				.compact();
	}

	public String createRefreshToken(Long userNo, int i) {
		Date now = new Date();
		return Jwts.builder()
				.setSubject(String.valueOf(userNo)) // 페이로드에 저장할 id
				.setIssuedAt(now) // 토큰 발행시간
				.setExpiration(new Date(System.currentTimeMillis() + (1000 * 60 * 60 * 24 * i))) // 토큰 만료시간
				.signWith(refreshKey, SignatureAlgorithm.HS256) // 서명에 사용할 키값과, 알고리즘
				.compact();
	}

	public Long getUserNo(String token) {
		return Long.valueOf(
				Jwts.parserBuilder()
				.setSigningKey(key)
				.build()
				.parseClaimsJws(token)
				.getBody()
				.getSubject()
				);
	}

	public Long parseRefresh(String token) {
		return Long.valueOf(
				Jwts.parserBuilder()
				.setSigningKey(refreshKey)
				.build()
				.parseClaimsJws(token)
				.getBody()
				.getSubject()
				);
	}

	public List<String> getRoles(String token) {
		Claims claims = Jwts.parserBuilder()
	            .setSigningKey(key)   // AccessToken용 키
	            .build()
	            .parseClaimsJws(token)
	            .getBody();

	    Object rolesObject = claims.get("roles"); // 토큰 생성 시 claim에 넣은 roles
	    if (rolesObject instanceof List<?> rolesList) {
	        return rolesList.stream()
	        	.filter(role -> role instanceof String)
	        	.map(role -> (String) role)
	        	.toList();
	    }

	    return List.of();
	}
	
	public Long getDeptcode(String token) {
		Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
		return claims.get("depId", Long.class);
	}
	
}
