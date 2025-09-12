package com.kh.coreflow.humanmanagement.model.service;

import java.util.List;
import java.util.Map;

import com.kh.coreflow.humanmanagement.model.dto.AttendanceDto.AttendanceInfo;
import com.kh.coreflow.humanmanagement.model.dto.AttendanceDto.PutCheckOut;
import com.kh.coreflow.humanmanagement.model.dto.AttendanceDto.VacType;
import com.kh.coreflow.humanmanagement.model.dto.AttendanceDto.VacTypeUpdate;

public interface AttendanceService {

	List<AttendanceInfo> memAttendance(Map<String, Object> paramsLocalDate);

	List<AttendanceInfo> perAttendance(Map<String, Object> params);

	int checkIn(Map<String, Object> params);

	int checkOut(PutCheckOut checkOut);

	List<VacType> vacTypeList(VacType vacType);

	int vacUpdate(VacTypeUpdate vacTypeUpdate);
}