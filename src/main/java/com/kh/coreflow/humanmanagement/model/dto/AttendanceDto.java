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
		private String vacName;
	}
	
	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	public static class PutCheckIn{
		private int attId;
		private String attDate;
		private String checkInTime;
		private int status;
	}
	
	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	public static class PutCheckOut{
		private int attId;
		private String checkOutTime;
	}
	
	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	public static class VacType{
		private Integer vacCode;
		private String vacName;
	}
	
	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	public static class VacTypeUpdate{
		private Integer attId;
		private int vacCode;
	}
}