package com.kh.coreflow.member.model.dao;

import java.util.List;

import com.kh.coreflow.member.model.dto.MemberDto.MemberLite;

public interface MemberDao {

	List<MemberLite> searchMembers(String query, Integer limit, Long depId);


	
}
