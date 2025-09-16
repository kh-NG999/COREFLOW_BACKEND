package com.kh.coreflow.humanmanagement.model.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.kh.coreflow.humanmanagement.model.dao.MemberDao;
import com.kh.coreflow.humanmanagement.model.dto.MemberDto.Department;
import com.kh.coreflow.humanmanagement.model.dto.MemberDto.MemberPatch;
import com.kh.coreflow.humanmanagement.model.dto.MemberDto.MemberPost;
import com.kh.coreflow.humanmanagement.model.dto.MemberDto.MemberResponse;
import com.kh.coreflow.humanmanagement.model.dto.MemberDto.Position;
import com.kh.coreflow.mail.service.MailService;
import com.kh.coreflow.model.dao.AuthDao;
import com.kh.coreflow.model.dto.UserDto.User;
import com.kh.coreflow.model.dto.UserDto.UserAuthority;
import com.kh.coreflow.security.factory.UserFactory;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService{
	private final MemberDao dao;
	private final AuthDao authDao;
	private final PasswordEncoder encoder;
	private final UserFactory userFactory;
	private final MailService mailService;
	
	@Override
	public List<Department> deptList() {
		return dao.deptList();
	}

	@Override
	public List<Department> deptDetailList(int parentId) {
		return dao.deptDetailList(parentId);
	}

	@Override
	public List<Position> posiList() {
		return dao.posiList();
	}
	
	@Override
	public List<MemberResponse> memberList(Map<String, String> searchParams) {
		return dao.memberList(searchParams);
	}

	@Override
	public MemberResponse memberDetail(Long userNo) {
		return dao.memberDetail(userNo);
	}

	@Override
	public int memberInsert(MemberPost member) {
		User user = userFactory.createIncompleteUser(member);
		
		String tempPwd = user.getUserPwd();
		user.setUserPwd(encoder.encode(tempPwd));
		List<String> roles = getAuth(user.getDepId());
		user.setRoles(roles);
		
		int result = dao.memberInsert(user);
		UserAuthority auth = new UserAuthority();
		auth.setUserNo(authDao.findUserNoByEmail(user.getEmail()));
		auth.setRoles(roles);
		authDao.insertUserRole(auth);
		
		mailService.sendTemporaryPassword(user, tempPwd);
		
		return result;
	}
	
	private List<String> getAuth(int depId){
		List<String> roles = new ArrayList<String>();
		roles.add("ROLE_USER");
		if(depId == 3 || depId == 4 || depId == 5) {
			roles.add("ROLE_HR");
		}
		return roles;
	}
	
	@Override
	public int memberUpdate(MemberPatch member) {
		return dao.memberUpdate(member);
	}

	@Override
	public int memberDelete(Long userNo) {
		return dao.memberDelete(userNo);
	}
}