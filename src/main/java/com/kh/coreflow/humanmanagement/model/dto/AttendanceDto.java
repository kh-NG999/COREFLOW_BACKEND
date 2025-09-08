package com.kh.coreflow.humanmanagement.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public class AttendanceDto {
	
	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	public static class AttendanceInfo{
		private String attDate;
		private String userName;
		private String depName;
		private String posName;
		private String checkInTime;
		private String checkOutTime;
		private int status;
	}
}
