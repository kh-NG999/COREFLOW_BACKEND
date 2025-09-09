package com.kh.coreflow.member.model.service;

import java.util.List;

import com.kh.coreflow.member.model.dto.MemberDto;

public interface MemberService{

	List<MemberDto.MemberLite> search(String query, Integer limit, Long depId);

}
