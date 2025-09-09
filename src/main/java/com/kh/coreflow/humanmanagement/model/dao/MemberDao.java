package com.kh.coreflow.humanmanagement.model.dao;

import java.util.List;
import java.util.Map;

import com.kh.coreflow.humanmanagement.model.dto.MemberDto.Department;
import com.kh.coreflow.humanmanagement.model.dto.MemberDto.MemberResponse;
import com.kh.coreflow.humanmanagement.model.dto.MemberDto.Position;

public interface MemberDao {

	List<Department> deptList();

	List<Department> deptDetailList(int parentId);
	
	List<Position> posiList();

	List<MemberResponse> memberList(Map<String, Object> params);

	MemberResponse memberDetail(Map<String, Object> params);

	int memberInsert(Map<String, Object> params);
	
	int memberUpdate(Map<String, Object> params);

	int memberDelete(Map<String, Object> params);
}