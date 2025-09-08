package com.kh.coreflow.humanmanagement.model.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.kh.coreflow.humanmanagement.model.dao.VacationDao;
import com.kh.coreflow.humanmanagement.model.dto.VacationDto.MemberChoice;
import com.kh.coreflow.humanmanagement.model.dto.VacationDto.MemberVacation;
import com.kh.coreflow.humanmanagement.model.dto.VacationDto.VacationInfo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class VacationServiceImpl implements VacationService{
	private final VacationDao dao;

	public List<VacationInfo> vacInfo() {
		return dao.vacInfo();
	}

	@Override
	public List<MemberChoice> memChoice(String userName) {
		return dao.memChoice(userName);
	}

	@Override
	public List<MemberVacation> memVacation(int userNo) {
		return dao.memVacation(userNo);
	}
}