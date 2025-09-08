package com.kh.coreflow.humanmanagement.model.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.kh.coreflow.humanmanagement.model.dao.AttendanceDao;
import com.kh.coreflow.humanmanagement.model.dto.AttendanceDto.AttendanceInfo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AttendanceServiceImpl implements AttendanceService{
	private final AttendanceDao dao;

	public List<AttendanceInfo> attInfo(Map<String, Object> params) {
		return dao.attInfo(params);
	}

}
