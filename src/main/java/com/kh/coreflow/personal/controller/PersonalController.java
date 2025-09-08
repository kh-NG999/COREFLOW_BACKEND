package com.kh.coreflow.personal.controller;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.kh.coreflow.model.dto.UserDto;
import com.kh.coreflow.model.dto.UserDto.User;
import com.kh.coreflow.personal.model.service.UserService;
import com.kh.coreflow.security.CustomUserDetails;
import com.kh.coreflow.security.model.service.AuthService;

import jakarta.servlet.ServletContext;

@Controller
@RequestMapping("/mypage")
public class PersonalController {

	private final AuthService authService;
	private final UserService userService;
	
	public PersonalController(AuthService authService, UserService userService) {
		this.authService = authService;
		this.userService = userService;
	}
	
	@GetMapping
	public String myPage(Model model, @AuthenticationPrincipal CustomUserDetails userDetails) {
		int userNo = userDetails.getUserNo();
		Optional<User> user = authService.findUserByUserNo(userNo);
		model.addAttribute("user" ,user);
		
		List<Object> mySchedule = userService.getMySchedule(userNo);
		model.addAttribute("mySchedule" ,mySchedule);
		
		return "/mypage";
	}
	
	
}














