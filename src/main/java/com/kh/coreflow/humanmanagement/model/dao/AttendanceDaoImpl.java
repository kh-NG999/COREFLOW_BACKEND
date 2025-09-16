package com.kh.coreflow.humanmanagement.model.dao;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import com.kh.coreflow.humanmanagement.model.dto.AttendanceDto.AttendanceInfo;
import com.kh.coreflow.humanmanagement.model.dto.AttendanceDto.PutCheckOut;
import com.kh.coreflow.humanmanagement.model.dto.AttendanceDto.VacTypeUpdate;
import com.kh.coreflow.humanmanagement.model.dto.AttendanceDto.VacType;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class AttendanceDaoImpl implements AttendanceDao{
	private final SqlSessionTemplate session;

	@Override
	public List<AttendanceInfo> memAttendance(Map<String, Object> params) {
		return session.selectList("attendance.memAttendance",params);
	}

	@Override
	public List<AttendanceInfo> perAttendance(Map<String, Object> params) {
		return session.selectList("attendance.perAttendance",params);
	}

	@Override
	public int checkIn(Map<String, Object> params) {
		return session.insert("attendance.checkIn",params);
	}

	@Override
	public int checkOut(PutCheckOut checkOut) {
		return session.update("attendance.checkOut",checkOut);
	}

	@Override
	public List<VacType> vacTypeList(VacType vacType) {
		return session.selectList("attendance.vacTypeList",vacType);
	}

	@Override
	public int vacTypeUpdate(VacTypeUpdate vacTypeUpdate) {
		return session.update("attendance.vacTypeUpdate",vacTypeUpdate);
	}
}