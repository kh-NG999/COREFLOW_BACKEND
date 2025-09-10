package com.kh.coreflow.humanmanagement.model.service;

import java.util.List;
import java.util.Map;

import com.kh.coreflow.humanmanagement.model.dto.MemberDto;
import com.kh.coreflow.humanmanagement.model.dto.MemberDto.Department;
import com.kh.coreflow.humanmanagement.model.dto.MemberDto.MemberPatch;
import com.kh.coreflow.humanmanagement.model.dto.MemberDto.MemberPost;
import com.kh.coreflow.humanmanagement.model.dto.MemberDto.MemberResponse;
import com.kh.coreflow.humanmanagement.model.dto.MemberDto.Position;

public interface MemberServiceImpl {

	List<Department> deptList();

	List<Department> deptDetailList(int parentId);
	
	List<Position> posiList();
	
	List<MemberResponse> memberList(Map<String, String> searchParams);

	MemberResponse memberDetail(int userNo);

	int memberInsert(MemberPost member);
	
	int memberUpdate(MemberPatch member);

	int memberDelete(int userNo);
	
	//남건후
	List<MemberDto.MemberLite> search(String query, Integer limit, Long depId);
	List<MemberDto.DepartmentLite> findAll();

}