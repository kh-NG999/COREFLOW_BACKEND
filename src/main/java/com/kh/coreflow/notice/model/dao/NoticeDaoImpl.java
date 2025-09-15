package com.kh.coreflow.notice.model.dao;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import com.kh.coreflow.notice.model.dto.NoticeDto.NoticeResponse;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class NoticeDaoImpl implements NoticeDao{
	private final SqlSessionTemplate session;

	@Override
	public List<NoticeResponse> notiList() {
		return session.selectList("notice.notiList");
	}

}
