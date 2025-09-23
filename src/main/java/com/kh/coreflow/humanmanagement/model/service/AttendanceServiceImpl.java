package com.kh.coreflow.humanmanagement.model.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.kh.coreflow.humanmanagement.model.dao.AttendanceDao;
import com.kh.coreflow.humanmanagement.model.dto.AttendanceDto.AttendanceInfo;
import com.kh.coreflow.humanmanagement.model.dto.AttendanceDto.PutCheckOut;
import com.kh.coreflow.humanmanagement.model.dto.AttendanceDto.VacTypeUpdate;
import com.kh.coreflow.humanmanagement.model.dto.AttendanceDto.VacType;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AttendanceServiceImpl implements AttendanceService{
	private final AttendanceDao dao;

	@Override
	public List<AttendanceInfo> memAttendance(Map<String, Object> params) {
		return dao.memAttendance(params);
	}

	@Override
	public List<AttendanceInfo> perAttendance(Map<String, Object> params) {
		return dao.perAttendance(params);
	}

	@Override
	public int checkIn(Map<String, Object> params) {
		return dao.checkIn(params);
	}

	@Override
	public int checkOut(PutCheckOut checkOut) {
		return dao.checkOut(checkOut);
	}

	@Override
	public List<VacType> vacTypeList(VacType vacType) {
		return dao.vacTypeList(vacType);
	}

	@Override
	public int vacUpdate(VacTypeUpdate vacTypeUpdate) {
		return dao.vacTypeUpdate(vacTypeUpdate);
	}
}