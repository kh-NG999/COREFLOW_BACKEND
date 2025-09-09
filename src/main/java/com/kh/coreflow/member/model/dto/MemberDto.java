package com.kh.coreflow.member.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

public class MemberDto {

	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	@Builder
	public static class MemberLite{
		private Long userNo;
		private Long depId;
		private Long posId;
		private String userName;
		private String email;
	}
}
