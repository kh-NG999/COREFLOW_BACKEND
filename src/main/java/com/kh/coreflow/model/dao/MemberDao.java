package com.kh.coreflow.model.dao;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import com.kh.coreflow.model.dto.MemberDto.MemberPost;
import com.kh.coreflow.model.dto.MemberDto.MemberPut;
import com.kh.coreflow.model.dto.MemberDto.MemberResponse;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class MemberDao implements MemberDaoImpl{
	private final SqlSessionTemplate session;

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
	public int memberUpdate(MemberPut member) {
		return session.update("member.memberUpdate",member);
	}

	@Override
	public int memberDelete(int userNo) {
		return session.delete("member.memberDelete",userNo);
	}
	
}