package com.kh.coreflow.humanmanagement.model.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.kh.coreflow.humanmanagement.model.dao.OrganizationDao;
import com.kh.coreflow.humanmanagement.model.dto.MemberDto.MemberResponse;
import com.kh.coreflow.humanmanagement.model.dto.OrganizationDto.ChildDep;
import com.kh.coreflow.humanmanagement.model.dto.OrganizationDto.ParentDep;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrganizationServiceImpl implements OrganizationService{
	private final OrganizationDao dao;

	@Override
	public List<ParentDep> parentDeptList() {
		return dao.parentDeptList();
	}

	@Override
	public List<ChildDep> childDeptList(int parentId) {
		return dao.childDeptList(parentId);
	}

	@Override
	public List<MemberResponse> memberList(int depId) {
		return dao.memberList(depId);
	}
}
