package com.kh.coreflow.humanmanagement.model.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.kh.coreflow.humanmanagement.model.dao.VacationDao;
import com.kh.coreflow.humanmanagement.model.dao.VacationDaoImpl;
import com.kh.coreflow.humanmanagement.model.dto.VacationDto.VacationInfo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class VacationServiceImpl implements VacationService{
	private final VacationDao dao;

	public List<VacationInfo> vacationInfo() {
		return dao.vacationInfo();
	}

}
