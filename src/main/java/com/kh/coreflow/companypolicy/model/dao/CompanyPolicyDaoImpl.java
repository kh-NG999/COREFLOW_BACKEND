package com.kh.coreflow.companypolicy.model.dao;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import com.kh.coreflow.companypolicy.model.dto.CompanyPolicyDto.CompanyPolicy;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class CompanyPolicyDaoImpl implements CompanyPolicyDao {
	private final SqlSessionTemplate session;

	@Override
	public List<CompanyPolicy> getPolicies() {
		return session.selectList("cpolicy.getPolicies");
	}

	@Override
	public int addPolicy(CompanyPolicy policy) {
		return session.insert("cpolicy.addPolicy", policy);
	}
}
