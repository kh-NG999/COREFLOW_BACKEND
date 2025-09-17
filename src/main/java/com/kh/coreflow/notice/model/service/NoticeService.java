package com.kh.coreflow.notice.model.service;

import java.util.List;
import java.util.Map;

import com.kh.coreflow.notice.model.dto.NoticeDto.NoticeResponse;

public interface NoticeService {

	List<NoticeResponse> notiList();
	
	List<NoticeResponse> notiList(Map<String, String> params);

	int notiInsert(Map<String, Object> params);
	
}
