package com.kh.coreflow.notice.model.dao;

import java.util.List;

import com.kh.coreflow.notice.model.dto.NoticeDto.NoticeResponse;

public interface NoticeDao {

	List<NoticeResponse> notiList();

}
