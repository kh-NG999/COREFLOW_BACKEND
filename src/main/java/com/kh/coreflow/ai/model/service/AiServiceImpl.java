package com.kh.coreflow.ai.model.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.kh.coreflow.ai.model.dao.AiDao;
import com.kh.coreflow.ai.model.dto.AiDto.AiChatHistory;
import com.kh.coreflow.ai.model.dto.AiDto.AiChatSession;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AiServiceImpl implements AiService {
	private final AiDao dao;

	@Override
	public List<AiChatSession> getSessions(Long userNo) {
		return dao.getSessions(userNo);
	}

	@Override
	public Integer countAiUsage(Long userNo) {
		return dao.countAiUsage(userNo);
	}

	@Override
	public int insertAiUsage(Map<String, Object> map) {
		return dao.insertAiUsage(map);
	}

	@Override
	public int updateAiUsage(Map<String, Object> map) {
		return dao.updateAiUsage(map);
	}

	@Override
	public int insertSession(Map<String, Object> map) {
		return dao.insertSession(map);
	}

	@Override
	public int updateSession(Long sessionId) {
		return dao.updateSession(sessionId);
	}

	@Override
	public int insertHistory(Map<String, Object> map) {
		return dao.insertHistory(map);
	}

	@Override
	public List<AiChatHistory> getHistories(Long sessionId) {
		return dao.getHistories(sessionId);
	}

	@Override
	public int deleteChatSession(Long sessionId) {
		return dao.deleteChatSession(sessionId);
	}
}









