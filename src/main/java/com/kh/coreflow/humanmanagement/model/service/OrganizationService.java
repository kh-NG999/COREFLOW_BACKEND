package com.kh.coreflow.humanmanagement.model.service;

import java.util.List;

import com.kh.coreflow.humanmanagement.model.dto.MemberDto.MemberResponse;
import com.kh.coreflow.humanmanagement.model.dto.OrganizationDto.ChildDep;
import com.kh.coreflow.humanmanagement.model.dto.OrganizationDto.ParentDep;

public interface OrganizationService {

	List<ParentDep> parentDeptList();

	List<ChildDep> childDeptList(int parentId);

	List<MemberResponse> memberList(int depId);
}