package com.kh.coreflow.security.factory;

import java.security.SecureRandom;
import java.util.Date;

import com.kh.coreflow.model.dto.UserDto.User;

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
     * 프론트에서 받은 기본 정보(name, deptId, posId, email)를 기반으로
     * INCOMPLETE 상태의 임시 계정을 생성
     */
    public static User createIncompleteUser(String email, String name, int deptId, int posId) {
        return User.builder()
                .email(email)
                .userPwd(generateRandomPassword(10)) // 서버에서 자동 생성
                .name(name)    	// 프론트 입력값
                .deptId(deptId) // 프론트 select-option
                .posId(posId)   // 프론트 select-option
                .profile("/images/default-profile.png")	// 기본 프로필
                .hireDate(new Date())	// 계정 생성일
                .phone("미입력")
                .address("미입력")
                .status("INCOMPLETE")	// 미완성 계정
                .build();
    }
}
