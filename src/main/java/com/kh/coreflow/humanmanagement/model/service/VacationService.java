package com.kh.coreflow.humanmanagement.model.service;

import java.util.List;

import com.kh.coreflow.humanmanagement.model.dto.VacationDto.VacationInfo;

public interface VacationService {

	List<VacationInfo> vacationInfo();

	int vacInfoUpdate(VacationInfo vacInfo);

}
