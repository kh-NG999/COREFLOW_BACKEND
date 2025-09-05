package com.kh.coreflow.ai.model.dao;

import java.util.List;
import java.util.Map;

import com.kh.coreflow.ai.model.dto.AiDto.AiChatSession;

public interface AiDao {

	List<AiChatSession> getSessions(Long userNo);

	Integer countAiUsage(Long userNo);

	int insertAiUsage(Map<String, Object> map);

	int updateAiUsage(Map<String, Object> map);

}
