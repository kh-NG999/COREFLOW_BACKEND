package com.kh.coreflow.humanmanagement.model.dao;

import java.util.List;
import java.util.Map;

import com.kh.coreflow.humanmanagement.model.dto.AttendanceDto.AttendanceInfo;

public interface AttendanceDao {
	
	List<AttendanceInfo> attInfo(Map<String, Object> params);

}
