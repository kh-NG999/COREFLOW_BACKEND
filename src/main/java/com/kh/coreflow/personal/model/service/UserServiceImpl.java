package com.kh.coreflow.personal.model.service;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.kh.coreflow.model.dao.AuthDao;
import com.kh.coreflow.model.dto.UserDto.User;
import com.kh.coreflow.model.dto.UserDto.UserUpdate;
import com.kh.coreflow.security.model.service.AuthService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{
	
	@Autowired
	private final AuthService authService;
	
	@Autowired
	private final AuthDao authDao;
	
	private final PasswordEncoder encoder;
	
	
	
	@Override
	public List<Object> getMySchedule(Long userNo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updateMyInfo(UserUpdate userUpdate, MultipartFile profileImage) {
		Long userNo = userUpdate.getUserNo();
		
		User user = authService.findUserByUserNo(userNo)
				.orElseThrow(() -> new RuntimeException("에러가 발생하였습니다"));
	}

	@Override
	public void updatePassword(Long userNo, String userPwd, String string) {
		User user = authDao.findUserByUserNo(userNo)
                .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));
		String encodedPwd = encoder.encode(string);
		authDao.updatePwd(user.getEmail(), encodedPwd);
	}

	@Override
	public void updatePhone(Long userNo, String string) {
		authDao.updatePhone(userNo, string);
	}

	@Override
	public void updateAddress(Long userNo, String string) {
		authDao.updateAddress(userNo, string);
	}
	
	@Override
	@Transactional
	public String updateProfileImage(Long userNo, MultipartFile profile) {
		String webPath = "/resources/static/images/p/";
        String serverFolderPath = "src/main/resources/static/images/p/";
        File dir = new File(serverFolderPath);
		System.out.println(dir.getAbsolutePath());
        if(!dir.exists()) dir.mkdirs();
        
        System.out.println(profile.isEmpty());
        
        Map<String, Object> imageUpdate = new HashMap<>();

        String originName = profile.getOriginalFilename();
        String currentTime = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        String ext = originName.substring(originName.lastIndexOf("."));
        String changeName = currentTime + ext;
        try {
            profile.transferTo(new File(dir.getAbsolutePath() + "\\" + changeName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        imageUpdate.put("originName", originName);
        imageUpdate.put("changeName", changeName);
        imageUpdate.put("currentTime", currentTime);
        imageUpdate.put("userNo", userNo);
        
        int count = authDao.checkProfileImage(userNo);
        
        if(count > 0) authDao.updateProfileImage(imageUpdate);
        else authDao.insertProfileImage(imageUpdate);
        
        System.out.println("DB 반영 결과: " + count);
        
        File savedFile = new File(dir.getAbsolutePath() + "\\" + changeName);
        
        System.out.println("파일 저장됨? " + savedFile.exists());

        return webPath + changeName; // 반환할 URL
	}

	
	
	
}
