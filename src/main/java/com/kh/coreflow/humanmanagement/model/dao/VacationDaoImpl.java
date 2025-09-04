package com.kh.coreflow.humanmanagement.model.dao;

import java.util.List;

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
	public List<MemberChoice> memChoice() {
		return session.selectList("vacation.memChoice");
	}

	@Override
	public List<MemberVacation> memVacation(int userNo) {
		return session.selectList("vacation.memVacation",userNo);
	}
}