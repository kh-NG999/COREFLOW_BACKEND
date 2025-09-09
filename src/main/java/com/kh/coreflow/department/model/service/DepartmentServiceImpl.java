package com.kh.coreflow.department.model.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.kh.coreflow.department.model.dao.DepartmentDao;
import com.kh.coreflow.department.model.dto.DepartmentDto.DepartmentLite;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DepartmentServiceImpl implements DepartmentService{
	
	private final DepartmentDao dao;
	
	@Override
	public List<DepartmentLite> findAll() {
		return dao.findAll();
	}

	
}
