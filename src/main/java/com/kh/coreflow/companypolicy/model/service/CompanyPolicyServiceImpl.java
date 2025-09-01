package com.kh.coreflow.companypolicy.model.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kh.coreflow.companypolicy.model.dao.CompanyPolicyDao;
import com.kh.coreflow.companypolicy.model.dto.CompanyPolicyDto.*;

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

	@Override
	@Transactional
	public int updatePolicy(CompanyPolicyModHistory history) {
		int result = dao.updatePolicy(history);
		result = dao.saveUpdatePolicyHistory(history);
		
		return result;
	}

	@Override
	public int deletePolicy(Long policyId) {
		return dao.deletePolicy(policyId);
	}
}
