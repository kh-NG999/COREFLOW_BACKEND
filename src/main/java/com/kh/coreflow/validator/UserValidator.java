package com.kh.coreflow.validator;

import com.kh.coreflow.humanmanagement.model.dto.MemberDto.MemberPost;

//유효성 검사
public class UserValidator {

	public boolean validateStep1(MemberPost req) {
		if(req == null) return false;
		
		if(req.getEmail() == null || !isValidEmail(req.getEmail())) return false; 	// 이메일
        
        return true;
	}

	private boolean isValidEmail(String email) {
		return false;
	}
	
	private boolean isValidPhone(String phone) {
		return false;
	}
	
	
	
}
