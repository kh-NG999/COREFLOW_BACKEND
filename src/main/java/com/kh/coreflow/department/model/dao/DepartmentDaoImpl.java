package com.kh.coreflow.department.model.dao;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import com.kh.coreflow.department.model.dto.DepartmentDto.DepartmentLite;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class DepartmentDaoImpl implements DepartmentDao{
	
	private final SqlSessionTemplate sql;
	
	@Override
	public List<DepartmentLite> findAll() {
		return sql.selectList("department.findAll");
	}

	
	
}
