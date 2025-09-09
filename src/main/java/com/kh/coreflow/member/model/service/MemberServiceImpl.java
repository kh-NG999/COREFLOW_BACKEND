package com.kh.coreflow.member.model.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.kh.coreflow.member.model.dao.MemberDao;
import com.kh.coreflow.member.model.dto.MemberDto.MemberLite;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService{

	private final MemberDao dao;

	@Override
	public List<MemberLite> search(String query, Integer limit, Long depId) {
		return dao.searchMembers(query, limit, depId);
	}
	
	
}
