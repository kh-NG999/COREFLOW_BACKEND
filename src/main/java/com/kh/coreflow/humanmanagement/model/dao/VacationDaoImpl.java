package com.kh.coreflow.humanmanagement.model.dao;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import com.kh.coreflow.humanmanagement.model.dto.VacationDto.MemberChoice;
import com.kh.coreflow.humanmanagement.model.dto.VacationDto.MemberVacation;
import com.kh.coreflow.humanmanagement.model.dto.VacationDto.VacationInfo;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class VacationDaoImpl implements VacationDao{
	private final SqlSessionTemplate session;

	@Override
	public List<VacationInfo> vacInfo() {
		return session.selectList("vacation.vacInfo");
	}

	@Override
	public List<MemberVacation> allVacation(Map<String, Object> params) {
		return session.selectList("vacation.allVacation",params);
	}

	@Override
	public List<MemberChoice> memChoice(String userName) {
		return session.selectList("vacation.memChoice",userName);
	}

	@Override
	public List<MemberVacation> memVacation(Map<String, Object> params) {
		return session.selectList("vacation.memVacation",params);
	}

	@Override
	public List<MemberVacation> perVacation(Map<String, Object> params) {
		return session.selectList("vacation.perVacation",params);
	}

}