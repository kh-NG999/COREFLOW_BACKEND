package com.kh.coreflow.humanmanagement.model.dao;

import java.util.List;
import java.util.Map;

import com.kh.coreflow.humanmanagement.model.dto.AttendanceDto.AttendanceInfo;
import com.kh.coreflow.humanmanagement.model.dto.AttendanceDto.PutCheckOut;
import com.kh.coreflow.humanmanagement.model.dto.AttendanceDto.VacTypeUpdate;
import com.kh.coreflow.humanmanagement.model.dto.AttendanceDto.VacType;

public interface AttendanceDao {
	
	List<AttendanceInfo> memAttendance(Map<String, Object> params);

	List<AttendanceInfo> perAttendance(Map<String, Object> params);

	int checkIn(Map<String, Object> params);

	int checkOut(PutCheckOut checkOut);

	List<VacType> vacTypeList(VacType vacType);

	int vacTypeUpdate(VacTypeUpdate vacTypeUpdate);
}