package com.kh.coreflow.model.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public class MemberDto {
	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	public static class MemberResponse{
		private int userNo;
		private String userName;
		private String email;
		private Date hireDate;
		private String depName;
		private String posName;
		private String phone;
		private int compId;
		private String compName;
		private String extention;
		private String address;
		private String addressDetail;
		private Date updateDate;
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
		private int compId;
		private String compName;
		private String extention;
		private String address;
		private String addressDetail;
		private String role;
		private Date updateDate;
	}
	
	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	public static class MemberPut{
		private int userNo;
		private String userName;
		private String email;
		private Date hireDate;
		private String depName;
		private String posName;
		private String phone;
		private int compId;
		private String compName;
		private String extention;
		private String address;
		private String addressDetail;
	}
}