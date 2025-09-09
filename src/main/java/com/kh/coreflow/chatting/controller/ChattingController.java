package com.kh.coreflow.chatting.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kh.coreflow.chatting.model.dto.ChattingDto.chatMessages;
import com.kh.coreflow.chatting.model.dto.ChattingDto.chatProfile;
import com.kh.coreflow.chatting.model.dto.ChattingDto.chatRooms;
import com.kh.coreflow.chatting.model.dto.ChattingDto.userFavorite;
import com.kh.coreflow.chatting.model.service.ChattingService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/chatting")
@RestController
public class ChattingController {
	
	@Autowired
	ChattingService chattingService;
	
	@GetMapping("myProfile")
	public ResponseEntity<chatProfile> myProfile(
			@AuthenticationPrincipal int userNo
			){
		chatProfile profile = chattingService.getMyProfile(userNo);
		log.info("profile : {}",profile);
		return ResponseEntity.ok(profile);
	}
	
	@GetMapping("/user")
	public ResponseEntity<List<chatProfile>> chatUser(
			@AuthenticationPrincipal int userNo
			){
		log.info("userNo : {}",userNo);
		List<chatProfile> list = chattingService.getChatProfiles(userNo);
		log.info("user List : {}",list);
		return ResponseEntity.ok(list);
	}
	
	@GetMapping("/favorites")
	public ResponseEntity<List<chatProfile>> favoriteUser(
			@AuthenticationPrincipal int userNo
			){
		List<chatProfile> list = chattingService.getFavoriteProfiles(userNo);
		log.info("favUser List : {}",list);
		return ResponseEntity.ok(list);
	}
	
	
	@PostMapping("/favorites")
	public ResponseEntity<Void> insertFavorite(
			@AuthenticationPrincipal int userNo,
			@RequestBody userFavorite favUser
			){
		favUser.setUserNo(userNo);
		int result = chattingService.insertFavoriteProfiles(favUser);
		
		return null;
	}

	@DeleteMapping("/favorites/{favUserNo}")
	public ResponseEntity<Void> deleteFavorite(
			@AuthenticationPrincipal int userNo,
			@PathVariable("favUserNo") int favUserNo
			){
		userFavorite favUser = new userFavorite();
		log.info("info:{}",favUserNo);
		favUser.setUserNo(userNo);
		favUser.setFavoriteUserNo(favUserNo);
		int result = chattingService.deleteFavoriteProfiles(favUser);
		
		return null;
	}
	
	@GetMapping("/private/{userNo}")
	public ResponseEntity<chatRooms> openPrivateChat(
			@AuthenticationPrincipal int userNo,
			@PathVariable("userNo") int partnerUserNo
			){
		
		HashMap<String,Object> mappingParameter = new HashMap<String,Object>();
		List<Integer> privateMember = new ArrayList<Integer>();
		privateMember.add(userNo);
		privateMember.add(partnerUserNo);
		
		
		mappingParameter.put("participantUserNos",privateMember);
		mappingParameter.put("partner", partnerUserNo);
		chatRooms resultRoom = chattingService.openChat(privateMember);
		if(resultRoom == null) {
			int answer = chattingService.makeChat(userNo,mappingParameter,"PRIVATE");
			resultRoom = chattingService.openChat(privateMember);
			if(resultRoom==null) {
				return ResponseEntity.badRequest().build();
			}
		}
		return ResponseEntity.ok(resultRoom);
	}
	
	@GetMapping("/room/{roomId}/messages")
	public ResponseEntity<List<chatMessages>> getMessages(
			@PathVariable("roomId") int roomId,
			@AuthenticationPrincipal int userNo
			){
		log.info("userNo : {}",userNo);
		log.info("Room Id : {}",roomId);
		List<chatMessages> list = chattingService.getMessages(roomId);
		log.info("messageList : {}",list);
		return ResponseEntity.ok(list);
	}
	
	
	@GetMapping("/myChattingRooms")
	public ResponseEntity<List<chatRooms>> myChattingRooms(
			@AuthenticationPrincipal int userNo
			){
		List<chatRooms> list = chattingService.getmyChattingRooms(userNo);
		return ResponseEntity.ok(list);
	}
	
	@PostMapping("/public")
	public ResponseEntity<Void> MakePublicRoom(
			@AuthenticationPrincipal int userNo,
			@RequestBody Map<String,Object> newChatParam
			){
		int result = chattingService.makeChat(userNo,newChatParam,"PUBLIC");
		return null;
	}
}
