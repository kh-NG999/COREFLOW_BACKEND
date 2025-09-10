package com.kh.coreflow.humanmanagement.model.service;

import java.util.List;
import java.util.Map;

import com.kh.coreflow.humanmanagement.model.dto.AttendanceDto.AttendanceInfo;

public interface AttendanceService {

	List<AttendanceInfo> memAttendance(Map<String, Object> paramsLocalDate);

	List<AttendanceInfo> perAttendance(Map<String, Object> params);

}
