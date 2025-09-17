package com.kh.coreflow.notice.model.dao;

import java.util.List;
import java.util.Map;

import com.kh.coreflow.notice.model.dto.NoticeDto.NoticeResponse;

public interface NoticeDao {

	List<NoticeResponse> notiList();
	
	List<NoticeResponse> notiList(Map<String, String> params);

}
