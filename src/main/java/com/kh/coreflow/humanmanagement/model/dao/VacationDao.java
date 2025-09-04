package com.kh.coreflow.humanmanagement.model.dao;

import java.util.List;

import com.kh.coreflow.humanmanagement.model.dto.VacationDto.MemberChoice;
import com.kh.coreflow.humanmanagement.model.dto.VacationDto.MemberVacation;
import com.kh.coreflow.humanmanagement.model.dto.VacationDto.VacationInfo;

public interface VacationDao {

	List<VacationInfo> vacInfo();

	List<MemberChoice> memChoice();

	List<MemberVacation> memVacation(int userNo);
}