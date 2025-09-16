package com.kh.coreflow.humanmanagement.model.dao;

import java.util.List;

import com.kh.coreflow.humanmanagement.model.dto.MemberDto.MemberResponse;
import com.kh.coreflow.humanmanagement.model.dto.OrganizationDto.ChildDep;
import com.kh.coreflow.humanmanagement.model.dto.OrganizationDto.ParentDep;

public interface OrganizationDao {

	List<ParentDep> parentDeptList();

	List<ChildDep> childDeptList(int parentId);

	List<MemberResponse> memberList(int depId);

}
