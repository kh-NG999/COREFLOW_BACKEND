package com.kh.coreflow.validator;

import com.kh.coreflow.model.dto.UserDto.UserCreate;

//유효성 검사
public class UserValidator {

	public boolean validateStep1(UserCreate req) {
		if(req == null) return false;
		
		if(req.getEmail() == null || !isValidEmail(req.getEmail())) return false; 	// 이메일
        if(req.getUserPwd() == null || req.getUserPwd().length() < 8) return false;	// 비밀번호
        if(req.getDepId() <= 0 || req.getPosId() <= 0) return false;// 부서, 직급
        
        return true;
	}

	private boolean isValidEmail(String email) {
		return false;
	}
	
	private boolean isValidPhone(String phone) {
		return false;
	}
	
	
	
}
