package com.kh.coreflow.humanmanagement.model.dao;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import com.kh.coreflow.humanmanagement.model.dto.AttendanceDto.AttendanceInfo;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class AttendanceDaoImpl implements AttendanceDao{
	private final SqlSessionTemplate session;

	public List<AttendanceInfo> attInfo(Map<String, Object> params) {
		return session.selectList("attendance.attInfo",params);
	}
}
