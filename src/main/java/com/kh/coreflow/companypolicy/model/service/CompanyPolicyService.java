package com.kh.coreflow.companypolicy.model.service;

import java.util.List;

import com.kh.coreflow.companypolicy.model.dto.CompanyPolicyDto.CompanyPolicy;

public interface CompanyPolicyService {

	List<CompanyPolicy> getPolicies();

	int addPolicy(CompanyPolicy policy);

}
