package com.kh.coreflow.humanmanagement.model.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

public class MemberDto {
	
	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	public static class Department{
		private int depId;
		private String depName;
		private Integer parentId;
	}
	
	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	public static class Position{
		private int posId;
		private String posName;
	}
	
	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	public static class MemberResponse{
		private int userNo;
		private String userName;
		private String email;
		private Date hireDate;
		private int depId;
		private String depName;
		private int posId;
		private String posName;
		private String phone;
		private String extension;
		private String address;
		private String addressDetail;
		private Date updateDate;
		private String status;
	}
	
	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	@Builder
	public static class MemberCreate{
		private int userNo;
		private String userName;
		private String email;
		private String userPwd;
		private Date hireDate;
		private int depId;
		private int posId;
		private String profile;
		private String phone;
		private String extension;
		private String address;
		private String addressDetail;
		private Date updateDate;
		private String status;
	}
	
	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	public static class MemberPost{
		private int userNo;
		private String userName;
		private String email;
		private Date hireDate;
		private String depName;
		private String posName;
		private String phone;
		private String extension;
		private String address;
		private String addressDetail;
		private Date updateDate;
		private String status;
	}
	
	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	public static class MemberPatch{
		private int userNo;
		private String userName;
		private String email;
		private Date hireDate;
		private int depId;
		private String depName;
		private int posId;
		private String posName;
		private String phone;
		private String extension;
		private String address;
		private String addressDetail;
		private Date updateDate;
		private String status;
	}
}