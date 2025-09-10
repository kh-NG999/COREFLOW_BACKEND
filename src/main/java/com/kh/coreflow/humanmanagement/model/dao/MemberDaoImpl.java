package com.kh.coreflow.humanmanagement.model.dao;

import java.util.List;
import java.util.Map;

import com.kh.coreflow.humanmanagement.model.dto.MemberDto.Department;
import com.kh.coreflow.humanmanagement.model.dto.MemberDto.DepartmentLite;
import com.kh.coreflow.humanmanagement.model.dto.MemberDto.MemberLite;
import com.kh.coreflow.humanmanagement.model.dto.MemberDto.MemberPatch;
import com.kh.coreflow.humanmanagement.model.dto.MemberDto.MemberPost;
import com.kh.coreflow.humanmanagement.model.dto.MemberDto.MemberResponse;
import com.kh.coreflow.humanmanagement.model.dto.MemberDto.Position;

public interface MemberDaoImpl {

	List<Department> deptList();

	List<Department> deptDetailList(int parentId);
	
	List<Position> posiList();

	List<MemberResponse> memberList(Map<String, String> searchParams);

	MemberResponse memberDetail(int userNo);

	int memberInsert(MemberPost member);
	
	int memberUpdate(MemberPatch member);

	int memberDelete(int userNo);

	List<MemberLite> searchMembers(String query, Integer limit, Long depId);

	List<DepartmentLite> findAll();
}