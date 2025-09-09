package com.kh.coreflow.model.dto;

import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

public class UserDto {

	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	public static class LoginRequest {
		private String email;
		private String Password;
	}
	
	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	@Builder
	public static class AuthResult{
		private String accessToken;
		private String refreshToken;
		private User user;
	}
	
	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	@Builder
	public static class User {
		private int userNo;
		private String userPwd;
		private String email;
		private String userName; // 전부 바꿔주기
		private int depId;
		private int posId;
		private String profile;
		private List<String> roles;
		private Date hireDate;
		private String phone;
		private String address;
		private String status = "INCOMPLETE";
	}
	
	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	@Builder
	public static class UserCreate{
		private int userNo;
		private String userPwd;
		private String email;
		private String userName;
		private int deptId;
		private int posId;
		private String profile;
		
		@Builder.Default
		private List<String> roles = List.of("ROLE_USER");
		private Date hireDate;
		
		@Builder.Default
		private String status = "INCOMPLETE";
		private String phone;
		private String address;
	}
	
	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	@Builder
	public static class FindPwdRequest {
		private String email;
		private String userName;
	}
	
	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	@Builder
	public static class UserUpdate{
		private int userNo;
		private String userPwd;
		private String phone;
		private String address;
		private String profile;
	}
	
	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	@Builder
	public static class UserCredential {
		private int userNo;
		private String userPwd;
	}
	
	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	@Builder
	public static class UserAuthority {
		private int userNo;
		private List<String> roles;
	}
	
	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	@Builder
	public static class UserDeptcode {
		private int userNo;
		private int depId;
	}
	 
}
