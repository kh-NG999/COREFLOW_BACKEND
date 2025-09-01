package com.kh.coreflow.companypolicy.model.dao;

import java.util.List;

import com.kh.coreflow.companypolicy.model.dto.CompanyPolicyDto.*;

public interface CompanyPolicyDao {

	List<CompanyPolicy> getPolicies();

	int addPolicy(CompanyPolicy policy);

	int updatePolicy(CompanyPolicyModHistory history);

	int saveUpdatePolicyHistory(CompanyPolicyModHistory history);

	int deletePolicy(Long policyId);

}
