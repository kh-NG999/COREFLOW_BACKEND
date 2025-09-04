package com.kh.coreflow.humanmanagement.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public class VacationDto {

	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	public static class VacationInfo{
		private int vacLv;
		private int workPrd;
		private int vacAmount;
	}
}
