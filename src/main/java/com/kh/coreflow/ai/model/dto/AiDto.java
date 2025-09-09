package com.kh.coreflow.ai.model.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

public class AiDto {
	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	@Builder
	public static class Ai {
		private Long modelId;
		private String modelName;
		private String activeStatus;
	}
	
	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	@Builder
	public static class AiUsage {
		private Long AiUsageId;
		private Long userNo;
		private Long modelId;
		private Date createdAt;
		private Date lastUsedAt;
		private Long tokensUsed;
	}
	
	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	@Builder
	public static class AiChatSession {
		private Long sessionId;
		private Long userNo;
		private Long modelId;
		private String title;
		private Date createdAt;
		private Date lastUsedAt;
	}
	
	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	@Builder
	public static class AiChatHistory {
		private Long historyId;
		private Long userNo;
		private Long sessionId;
		private String role;
		private String content;
		private Date createdAt;
	}
}









