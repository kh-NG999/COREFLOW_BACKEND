package com.kh.coreflow.humanmanagement.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public class AttendanceDto {
	
	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	public static class AttendanceInfo{
		private int attId;
		private String attDate;
		private String userName;
		private String depName;
		private String posName;
		private String checkInTime;
		private String checkOutTime;
		private int status;
	}
	
	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	public static class putCheckInTime{
		private String attDate;
		private int userNo;
		private String checkInTime;
		private int status;
	}
	
	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	public static class selelctAttId{
		private int attId;
	}
	
	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	public static class putCheckOutTime{
		private int attId;
		private int userNo;
		private String checkOutTime;
	}
}