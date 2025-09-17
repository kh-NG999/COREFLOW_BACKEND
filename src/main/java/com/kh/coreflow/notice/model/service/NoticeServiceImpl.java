package com.kh.coreflow.notice.model.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.kh.coreflow.notice.model.dao.NoticeDao;
import com.kh.coreflow.notice.model.dto.NoticeDto.NoticeResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NoticeServiceImpl implements NoticeService{
	private final NoticeDao dao;

	@Override
	public List<NoticeResponse> notiList() {
		return dao.notiList();
	}
	
	@Override
	public List<NoticeResponse> notiList(Map<String, String> params) {
		return dao.notiList(params);
	}

}
