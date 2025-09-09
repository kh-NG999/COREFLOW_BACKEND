package com.kh.coreflow.member.model.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import com.kh.coreflow.member.model.dto.MemberDto.MemberLite;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class MemberDaoImpl implements MemberDao {
	
	private final SqlSessionTemplate sql;
	
	@Override
	public List<MemberLite> searchMembers(String query, Integer limit, Long depId) {

		Map<String,Object> p = new HashMap<>();
		p.put("query", query);
		p.put("limit", limit);
		p.put("depId", depId);
		
		return sql.selectList("member.searchMembers", p);
	}
	

	
	
}
