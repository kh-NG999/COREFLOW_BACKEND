package com.kh.coreflow.companypolicy.model.service;

import java.util.List;

import com.kh.coreflow.companypolicy.model.dto.CompanyPolicyDto.*;

public interface CompanyPolicyService {

	List<CompanyPolicy> getPolicies();

	int addPolicy(CompanyPolicy policy);

	int updatePolicy(CompanyPolicyModHistory history);

	int deletePolicy(Long policyId);

}
