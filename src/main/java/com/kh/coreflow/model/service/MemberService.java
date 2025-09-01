package com.kh.coreflow.model.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.kh.coreflow.model.dao.MemberDaoImpl;
import com.kh.coreflow.model.dto.MemberDto.Department;
import com.kh.coreflow.model.dto.MemberDto.MemberPatch;
import com.kh.coreflow.model.dto.MemberDto.MemberPost;
import com.kh.coreflow.model.dto.MemberDto.MemberResponse;
import com.kh.coreflow.model.dto.MemberDto.Position;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberService implements MemberServiceImpl{
	private final MemberDaoImpl dao;

	@Override
	public List<MemberResponse> memberList(Map<String, String> searchParams) {
		return dao.memberList(searchParams);
	}

	@Override
	public MemberResponse memberDetail(int userNo) {
		return dao.memberDetail(userNo);
	}

	@Override
	public int memberInsert(MemberPost member) {
		return dao.memberInsert(member);
	}
	
	@Override
	public int memberUpdate(MemberPatch member) {
		return dao.memberUpdate(member);
	}

	@Override
	public int memberDelete(int userNo) {
		return dao.memberDelete(userNo);
	}

	@Override
	public List<Department> deptList() {
		return dao.deptList();
	}

	@Override
	public List<Position> posiList() {
		return dao.posiList();
	}

}