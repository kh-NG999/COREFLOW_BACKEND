package com.kh.coreflow.humanmanagement.model.service;

import java.util.List;
import java.util.Map;

import com.kh.coreflow.humanmanagement.model.dto.VacationDto.MemberChoice;
import com.kh.coreflow.humanmanagement.model.dto.VacationDto.MemberVacation;
import com.kh.coreflow.humanmanagement.model.dto.VacationDto.VacationInfo;

public interface VacationService {

	List<VacationInfo> vacInfo();

	List<MemberVacation> allVacation(Map<String, Object> params);
	
	List<MemberChoice> memChoice(String userName);

	List<MemberVacation> memVacation(Map<String, Object> params);

	List<MemberVacation> perVacation(Map<String, Object> params);

}