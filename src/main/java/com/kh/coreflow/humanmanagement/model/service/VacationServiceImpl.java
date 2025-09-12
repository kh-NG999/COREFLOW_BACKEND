package com.kh.coreflow.humanmanagement.model.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.kh.coreflow.humanmanagement.model.dao.VacationDao;
import com.kh.coreflow.humanmanagement.model.dto.VacationDto.LoginUser;
import com.kh.coreflow.humanmanagement.model.dto.VacationDto.MemberChoice;
import com.kh.coreflow.humanmanagement.model.dto.VacationDto.MemberVacation;
import com.kh.coreflow.humanmanagement.model.dto.VacationDto.VacType;
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
	public List<MemberVacation> allVacation(Map<String, Object> params) {
		return dao.allVacation(params);
	}
	
	@Override
	public List<MemberChoice> memChoice(String userName) {
		return dao.memChoice(userName);
	}

	@Override
	public List<MemberVacation> memVacation(Map<String, Object> params) {
		return dao.memVacation(params);
	}

	@Override
	public int vacStatusUpdate(Map<String, Object> params) {
		return dao.vacStatusUpdate(params);
	}
	
	@Override
	public LoginUser loginUserProfile(long userNo) {
		return dao.loginUserProfile(userNo);
	}

	@Override
	public List<MemberVacation> perVacation(Map<String, Object> params) {
		return dao.perVacation(params);
	}

	@Override
	public List<VacType> vacType() {
		return dao.vacType();
	}
	
	@Override
	public int putPerVac(Map<String, Object> params) {
		return dao.putPerVac(params);
	}
}