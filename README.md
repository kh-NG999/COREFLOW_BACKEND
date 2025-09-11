# COREFLOW_FINAL

시큐리티 관련 설정
- 권한은 총 3가지로 총관리자(ROLE_ADMIN), 인사과(ROLE_HR), 사용자(ROLE_USER)
- 컨트롤러에서 어노테이션 @PreAuthorize("hasRole('ADMIN')")를 추가하는 방식으로 적용이 가능하다
- 위 방법으로 조회는 모두가 가능하나 작성, 수정, 삭제 등은 해당 권한을 가진 계정으로만 가능하게 할 수 있다
- 관리자 전용 페이지가 필요하다면 SecutityConfig에서 .requestMatchers("/admin1/**", "/admin2/**").hasRole("ADMIN")으로 추가 가능하다
  (hasRole은 자동으로 문자열 앞에 ROLE_을 붙여서 자동매핑하기 때문)

- UserNo 뿐만이 아닌, DepId(부서)별로 서비스의 작동이 나뉘는 파트가 있기 때문에, Authentication 토큰 내에 userNo, depId가 추가되도록 바뀌었습니다.

- controller가 파라미터에서 Authentication auth를 통해 auth.getPrincipal()로 userNo만 가져올 수 있었습니다.

- 이제는 ((UserDeptcode)auth.getPrincipal()).getUserNo()와 ((UserDeptcode)auth.getPrincipal()).getDepId() 이 두가지를 통해 로그인 사용자의 userNo와 depId를 가져오실 수 있습니다.

- userDeptCode의 경우, userDto에 구성되어 있으니 확인 바랍니다. 기존에 auth.getPrincipal()만으로 구성해두신 분들은 백엔드 실행에서 오류가 나실 수 있으니 이 부분 수정 부탁드립니다.

- 로그인을 했음에도 401에러가 발생한것은 정상적인 현상입니다. api 서버로부터 응답받은 상태코드가 401인 경우 refresh토큰을 활용한 accessToken 재발급하기 위함이니 이점 인지해주시기 바랍니다.

- 마찬가지로 로그인 이후엔 refresh토큰 정보가 노출되나, 다른 페이지 이동시 이것이 null로 보이는 이유는 sameSite("Lax")설정 때문이며 이는 CSRF공격 방어용으로 추가된 설정이니 이점 인지해주시기 바랍니다.