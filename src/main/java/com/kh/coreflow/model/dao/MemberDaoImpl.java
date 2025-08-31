package com.kh.coreflow.model.dao;

import java.util.List;
import java.util.Map;

import com.kh.coreflow.model.dto.MemberDto.Department;
import com.kh.coreflow.model.dto.MemberDto.MemberPost;
import com.kh.coreflow.model.dto.MemberDto.MemberPut;
import com.kh.coreflow.model.dto.MemberDto.MemberResponse;
import com.kh.coreflow.model.dto.MemberDto.Position;

public interface MemberDaoImpl {

	List<MemberResponse> memberList(Map<String, String> searchParams);

	MemberResponse memberDetail(int userNo);

	int memberInsert(MemberPost member);
	
	int memberUpdate(MemberPut member);

	int memberDelete(int userNo);

	List<Department> deptList();

	List<Position> posiList();

}
