package com.kh.coreflow.notice.model.service;

import java.util.List;

import com.kh.coreflow.notice.model.dto.NoticeDto.NoticeResponse;

public interface NoticeService {

	List<NoticeResponse> notiList();
	
}
