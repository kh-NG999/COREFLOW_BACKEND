package com.kh.coreflow.companypolicy.model.dao;

import java.util.List;

import com.kh.coreflow.companypolicy.model.dto.CompanyPolicyDto.CompanyPolicy;

public interface CompanyPolicyDao {

	List<CompanyPolicy> getPolicies();

	int addPolicy(CompanyPolicy policy);

}
