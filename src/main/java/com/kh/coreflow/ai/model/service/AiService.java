package com.kh.coreflow.ai.model.service;

import java.util.List;
import java.util.Map;

import com.kh.coreflow.ai.model.dto.AiDto.AiChatHistory;
import com.kh.coreflow.ai.model.dto.AiDto.AiChatSession;

public interface AiService {

	List<AiChatSession> getSessions(Long userNo);

	Integer countAiUsage(Long userNo);

	int insertAiUsage(Map<String, Object> map);

	int updateAiUsage(Map<String, Object> map);

	int insertSession(Map<String, Object> map);

	int updateSession(Long sessionId);

	int insertHistory(Map<String, Object> map);

	List<AiChatHistory> getHistories(Long sessionId);

	int deleteChatSession(Long sessionId);

}
