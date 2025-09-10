package com.kh.coreflow.humanmanagement.model.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import com.kh.coreflow.humanmanagement.model.dto.MemberDto.Department;
import com.kh.coreflow.humanmanagement.model.dto.MemberDto.DepartmentLite;
import com.kh.coreflow.humanmanagement.model.dto.MemberDto.MemberLite;
import com.kh.coreflow.humanmanagement.model.dto.MemberDto.MemberPatch;
import com.kh.coreflow.humanmanagement.model.dto.MemberDto.MemberPost;
import com.kh.coreflow.humanmanagement.model.dto.MemberDto.MemberResponse;
import com.kh.coreflow.humanmanagement.model.dto.MemberDto.Position;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class MemberDao implements MemberDaoImpl{
	private final SqlSessionTemplate session;

	@Override
	public List<Department> deptList() {
		return session.selectList("member.deptList");
	}

	@Override
	public List<Department> deptDetailList(int parentId) {
		return session.selectList("member.deptDetailList",parentId);
	}
	
	@Override
	public List<Position> posiList() {
		return session.selectList("member.posiList");
	}
	
	@Override
	public List<MemberResponse> memberList(Map<String, String> searchParams) {
		return session.selectList("member.memberList",searchParams);
	}

	@Override
	public MemberResponse memberDetail(int userNo) {
		return session.selectOne("member.memberDetail",userNo);
	}

	@Override
	public int memberInsert(MemberPost member) {
		return session.insert("member.memberInsert",member);
	}
	
	@Override
	public int memberUpdate(MemberPatch member) {
		return session.update("member.memberUpdate",member);
	}

	@Override
	public int memberDelete(int userNo) {
		return session.delete("member.memberDelete",userNo);
	}

	@Override
	public List<MemberLite> searchMembers(String query, Integer limit, Long depId) {
		Map<String,Object> p = new HashMap<>();
		p.put("query", query);
		p.put("limit", limit);
		p.put("depId", depId);
		
		return session.selectList("member.searchMembers", p);
	}
	@Override
	public List<DepartmentLite> findAll() {
		return session.selectList("department.findAll");
	}
}