package com.kh.coreflow.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.kh.coreflow.security.filter.JWTAuthenticationFilter;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig {

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http,
			JWTAuthenticationFilter jwtFilter) throws Exception{
		http
			// cors관련 빈객체 등록
			.cors(cors -> cors.configurationSource(corsConfigurationSource()))
			// CSRF는 SPA어플리케이션에서 사용하지 않음
			.csrf(csrf -> csrf.disable())
			.exceptionHandling(e -> e
					.authenticationEntryPoint((req,res,ex) -> {
					// 인증 실패시 401처리
					res.sendError(HttpServletResponse.SC_UNAUTHORIZED, "UNAUTHORIZED");
					// 인증 실패시 403처리
					res.sendError(HttpServletResponse.SC_FORBIDDEN, "FORBIDDEN");
					}))
					.sessionManagement(
							management -> 
							management.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
					.authorizeHttpRequests(auth 
						-> auth
						.requestMatchers("/login**","/error").permitAll()
						.requestMatchers("/**").authenticated()
					);
		http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
		
		return http.build();
	}

	public CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration config = new CorsConfiguration();
		
		// 허용 Origin설정
		config.setAllowedOrigins(List.of("http://localhost:5173"));
		
		// 허용 메서드
		config.setAllowedMethods(List.of("GET","POST", "PUT", "PATCH", "DELETE"));
		config.setAllowedHeaders(List.of("*"));
		config.setExposedHeaders(List.of("Location", "Authorization"));
		config.setAllowCredentials(true); // 세션, 쿠키 허용
		config.setMaxAge(3600L); //요청정보 캐싱시간
		
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", config);
		
		return source;
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		PasswordEncoder encoder = new BCryptPasswordEncoder();
		return encoder;
	}
	
	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

}
