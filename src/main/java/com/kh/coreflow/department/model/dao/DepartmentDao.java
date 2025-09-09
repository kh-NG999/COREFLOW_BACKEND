package com.kh.coreflow.department.model.dao;

import java.util.List;

import com.kh.coreflow.department.model.dto.DepartmentDto.DepartmentLite;

public interface DepartmentDao {

	List<DepartmentLite> findAll();

}
