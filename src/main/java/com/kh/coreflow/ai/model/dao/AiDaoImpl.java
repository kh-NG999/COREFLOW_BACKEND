package com.kh.coreflow.ai.model.dao;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import com.kh.coreflow.ai.model.dto.AiDto.AiChatSession;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class AiDaoImpl implements AiDao {
	private final SqlSessionTemplate session;

	@Override
	public List<AiChatSession> getSessions(Long userNo) {
		return session.selectList("ai.getSessions", userNo);
	}

	@Override
	public Integer countAiUsage(Long userNo) {
		return session.selectOne("ai.countAiUsage", userNo);
	}

	@Override
	public int insertAiUsage(Map<String, Object> map) {
		return session.insert("ai.insertAiUsage", map);
	}

	@Override
	public int updateAiUsage(Map<String, Object> map) {
		return session.update("ai.updateAiUsage", map);
	}
}









