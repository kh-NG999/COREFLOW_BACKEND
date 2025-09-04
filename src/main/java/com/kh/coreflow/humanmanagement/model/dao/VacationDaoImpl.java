package com.kh.coreflow.humanmanagement.model.dao;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import com.kh.coreflow.humanmanagement.model.dto.VacationDto.VacationInfo;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class VacationDaoImpl implements VacationDao{
	private final SqlSessionTemplate session;

	@Override
	public List<VacationInfo> vacationInfo() {
		return session.selectList("vacation.vacationInfo");
	}
}
