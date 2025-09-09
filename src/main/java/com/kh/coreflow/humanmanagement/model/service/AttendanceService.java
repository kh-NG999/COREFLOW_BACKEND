package com.kh.coreflow.humanmanagement.model.service;

import java.util.List;
import java.util.Map;

import com.kh.coreflow.humanmanagement.model.dto.AttendanceDto.AttendanceInfo;

public interface AttendanceService {

	List<AttendanceInfo> attInfo(Map<String, Object> paramsLocalDate);

}
