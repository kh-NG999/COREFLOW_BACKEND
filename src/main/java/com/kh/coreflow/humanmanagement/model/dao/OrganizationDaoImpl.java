package com.kh.coreflow.humanmanagement.model.dao;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import com.kh.coreflow.humanmanagement.model.dto.MemberDto.MemberResponse;
import com.kh.coreflow.humanmanagement.model.dto.OrganizationDto.ChildDep;
import com.kh.coreflow.humanmanagement.model.dto.OrganizationDto.ParentDep;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class OrganizationDaoImpl implements OrganizationDao{
	private final SqlSessionTemplate session;

	@Override
	public List<ParentDep> parentDeptList() {
		return session.selectList("organization.parentDeptList");
	}

	@Override
	public List<ChildDep> childDeptList(int parentId) {
		return session.selectList("organization.childDeptList",parentId);
	}

	@Override
	public List<MemberResponse> memberList(int depId) {
		return session.selectList("organization.memberList",depId);
	}
}
