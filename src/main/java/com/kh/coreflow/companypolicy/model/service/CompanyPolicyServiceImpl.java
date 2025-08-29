package com.kh.coreflow.companypolicy.model.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.kh.coreflow.companypolicy.model.dao.CompanyPolicyDao;
import com.kh.coreflow.companypolicy.model.dto.CompanyPolicyDto.CompanyPolicy;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CompanyPolicyServiceImpl implements CompanyPolicyService {
	private final CompanyPolicyDao dao;

	@Override
	public List<CompanyPolicy> getPolicies() {
		return dao.getPolicies();
	}

	@Override
	public int addPolicy(CompanyPolicy policy) {
		return dao.addPolicy(policy);
	}
}
