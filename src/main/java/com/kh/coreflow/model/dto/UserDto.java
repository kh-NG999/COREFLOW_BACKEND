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
		private Long userNo;
		private String userName;
		private String email;
		private String userPwd;
		private List<String> roles;
		private int depId;
		private int posId;
		private Date hireDate;
		private String profile;
		private String extension;
		private String phone;
		private String address;
		private String addressDetail;
		private Date updateDate;
		private String status;
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
		private Long userNo;
		private String userPwd;
		private String phone;
		private String address;
		private String addressDetail;
		private String profile;
	}
	
	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	@Builder
	public static class UserCredential {
		private Long userNo;
		private String userPwd;
	}
	
	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	@Builder
	public static class UserAuthority {
		private Long userNo;
		private List<String> roles;
	}
	
	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	@Builder
	public static class UserDeptcode {
		private Long userNo;
		private int depId;
		
		@Override
	    public String toString() {
	        return String.valueOf(userNo);
	    }
	}
	
	
	 
}
