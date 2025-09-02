package com.kh.coreflow.companypolicy.model.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

public class CompanyPolicyDto {
	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	@Builder
	public static class CompanyPolicy {
		private Long policyId;
		private String title;
		private String content;
		private Long creatorUserNo;
		private Date createdAt;
		private Long modUserNo;
		private Date lastModifiedAt;
	}
	
	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	@Builder
	public static class CompanyPolicyModHistory {
		private Long modHistoryId;
		private Long policyId;
		private Long userNo;
		private Date modifiedAt;
		private String modContent;
	}
}









