# COREFLOW_FINAL

시큐리티 관련 설정
- 권한은 총 3가지로 총관리자(ROLE_ADMIN), 인사과(ROLE_HR), 사용자(ROLE_USER)
- 컨트롤러에서 어노테이션 @PreAuthorize("hasRole('ADMIN')")를 추가하는 방식으로 적용이 가능하다
- 위 방법으로 조회는 모두가 가능하나 작성, 수정, 삭제 등은 해당 권한을 가진 계정으로만 가능하게 할 수 있다
- 관리자 전용 페이지가 필요하다면 SecutityConfig에서 .requestMatchers("/admin1/**", "/admin2/**").hasRole("ADMIN")으로 추가 가능하다
  (hasRole은 자동으로 문자열 앞에 ROLE_을 붙여서 자동매핑하기 때문)
