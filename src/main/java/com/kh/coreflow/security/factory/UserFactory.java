package com.kh.coreflow.security.factory;

import java.security.SecureRandom;
import java.util.Date;

import com.kh.coreflow.humanmanagement.model.dto.MemberDto.MemberCreate;

public class UserFactory {

	private static final String CHAR_POOL = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final SecureRandom RANDOM = new SecureRandom();

    public static String generateRandomPassword(int length) {
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            int index = RANDOM.nextInt(CHAR_POOL.length());
            sb.append(CHAR_POOL.charAt(index));
        }
        return sb.toString();
    }

    /**
     * 인사, 관리자 권한(ROLE_HR, ROLE_ADMIN)을 가져야 쓸 수 있는 계정생성
     * 수정 필요
     */
    public static MemberCreate createIncompleteUser(String email, String userName, int depId, int posId) {
        return MemberCreate.builder()
                .email(email)
                .userPwd(generateRandomPassword(10))
                .userName(userName)	// 프론트 입력값
                .depId(depId) 	// 프론트 select-option
                .posId(posId)   // 프론트 select-option
                .profile("/images/p/default.png")	// 기본 프로필
                .hireDate(new Date())	// 계정 생성일
                .phone("미입력")
                .address("미입력")
                .status("T")	// 재직여부
                .build();
                
    }
}
