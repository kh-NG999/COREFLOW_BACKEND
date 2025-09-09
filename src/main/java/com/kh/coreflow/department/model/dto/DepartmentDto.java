package com.kh.coreflow.department.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

public class DepartmentDto {

	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	@Builder
	public static class DepartmentLite{
		private Long depId;
		private String depName;
		private Long parentId;
	}
}
