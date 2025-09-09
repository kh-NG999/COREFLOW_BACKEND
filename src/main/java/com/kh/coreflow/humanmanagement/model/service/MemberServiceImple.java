package com.kh.coreflow.humanmanagement.model.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.kh.coreflow.humanmanagement.model.dao.MemberDao;
import com.kh.coreflow.humanmanagement.model.dto.MemberDto.Department;
import com.kh.coreflow.humanmanagement.model.dto.MemberDto.MemberResponse;
import com.kh.coreflow.humanmanagement.model.dto.MemberDto.Position;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberServiceImple implements MemberService{
	private final MemberDao dao;
	
	@Override
	public List<Department> deptList() {
		return dao.deptList();
	}

	@Override
	public List<Department> deptDetailList(int parentId) {
		return dao.deptDetailList(parentId);
	}

	@Override
	public List<Position> posiList() {
		return dao.posiList();
	}
	
	@Override
	public List<MemberResponse> memberList(Map<String, Object> params) {
		return dao.memberList(params);
	}

	@Override
	public MemberResponse memberDetail(Map<String, Object> params) {
		return dao.memberDetail(params);
	}

	@Override
	public int memberInsert(Map<String, Object> params) {
		return dao.memberInsert(params);
	}
	
	@Override
	public int memberUpdate(Map<String, Object> params) {
		return dao.memberUpdate(params);
	}

	@Override
	public int memberDelete(Map<String, Object> params) {
		return dao.memberDelete(params);
	}
}