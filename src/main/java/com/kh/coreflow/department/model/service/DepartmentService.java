package com.kh.coreflow.department.model.service;

import java.util.List;

import com.kh.coreflow.department.model.dto.DepartmentDto;

public interface DepartmentService {

	List<DepartmentDto.DepartmentLite> findAll();

}
