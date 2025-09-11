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
import com.kh.coreflow.model.dto.UserDto.UserDeptcode;
import com.kh.coreflow.security.CustomUserDetails;

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
			@AuthenticationPrincipal UserDeptcode user,
			@AuthenticationPrincipal CustomUserDetails userDetails
			){
		//log.info("userDetail : {}",userDetails);
		chatProfile profile = chattingService.getMyProfile(user.getUserNo());
		//log.info("profile : {}",profile);
		return ResponseEntity.ok(profile);
	}
	
	@GetMapping("/user")
	public ResponseEntity<List<chatProfile>> chatUser(
			@AuthenticationPrincipal UserDeptcode user
			){
		//log.info("userNo : {}",user.getUserNo());
		List<chatProfile> list = chattingService.getChatProfiles(user.getUserNo());
		//log.info("user List : {}",list);
		return ResponseEntity.ok(list);
	}
	
	@GetMapping("/favorites")
	public ResponseEntity<List<chatProfile>> favoriteUser(
			@AuthenticationPrincipal UserDeptcode user
			){
		List<chatProfile> list = chattingService.getFavoriteProfiles(user.getUserNo());
		//log.info("favUser List : {}",list);
		return ResponseEntity.ok(list);
	}
	
	
	@PostMapping("/favorites")
	public ResponseEntity<Void> insertFavorite(
			@AuthenticationPrincipal UserDeptcode user,
			@RequestBody userFavorite favUser
			){
		favUser.setUserNo(user.getUserNo());
		int result = chattingService.insertFavoriteProfiles(favUser);
		
		return null;
	}

	@DeleteMapping("/favorites/{favUserNo}")
	public ResponseEntity<Void> deleteFavorite(
			@AuthenticationPrincipal UserDeptcode user,
			@PathVariable("favUserNo") Long favUserNo
			){
		userFavorite favUser = new userFavorite();
		log.info("info:{}",favUserNo);
		favUser.setUserNo(user.getUserNo());
		favUser.setFavoriteUserNo(favUserNo);
		int result = chattingService.deleteFavoriteProfiles(favUser);
		
		return null;
	}
	
	@GetMapping("/private/{userNo}")
	public ResponseEntity<chatRooms> openPrivateChat(
			@AuthenticationPrincipal UserDeptcode user,
			@PathVariable("userNo") Long partnerUserNo
			){
		
		HashMap<String,Object> mappingParameter = new HashMap<String,Object>();
		List<Long> privateMember = new ArrayList<Long>();
		privateMember.add(user.getUserNo());
		privateMember.add(partnerUserNo);
		
		
		mappingParameter.put("participantUserNos",privateMember);
		mappingParameter.put("partner", partnerUserNo);
		chatRooms resultRoom = chattingService.openChat(privateMember,"PRIVATE");
		if(resultRoom == null) {
			Long answer = chattingService.makeChat(user.getUserNo(),mappingParameter,"PRIVATE");
			resultRoom = chattingService.openChat(privateMember,"PRIVATE");
			if(resultRoom==null) {
				return ResponseEntity.badRequest().build();
			}
		}
		return ResponseEntity.ok(resultRoom);
	}
	
	@GetMapping("/room/{roomId}/messages")
	public ResponseEntity<List<chatMessages>> getMessages(
			@PathVariable("roomId") Long roomId,
			@AuthenticationPrincipal UserDeptcode user
			){
		//log.info("userNo : {}",user.getUserNo());
		//log.info("Room Id : {}",roomId);
		List<chatMessages> list = chattingService.getMessages(roomId);
		//log.info("messageList : {}",list);
		return ResponseEntity.ok(list);
	}
	
	
	@GetMapping("/myChattingRooms")
	public ResponseEntity<List<chatRooms>> myChattingRooms(
			@AuthenticationPrincipal UserDeptcode user
			){
		List<chatRooms> list = chattingService.getmyChattingRooms(user.getUserNo());
		log.info("myChattingRooms : {}",list);
		return ResponseEntity.ok(list);
	}
	
	@PostMapping("/public")
	public ResponseEntity<chatRooms> MakePublicRoom(
			@AuthenticationPrincipal UserDeptcode user,
			@RequestBody Map<String,Object> newChatParam
			){
		Long roomId = chattingService.makeChat(user.getUserNo(),newChatParam,"PUBLIC");
		chatRooms returnRoom = chattingService.getRoom(roomId);
		//log.info("returnRoom : {}, roomId : {}",returnRoom, roomId);
		return ResponseEntity.ok(returnRoom);
	}
	
	@GetMapping("/room/{roomId}")
	public ResponseEntity<chatRooms> getChatRoom(
			@PathVariable("roomId") Long roomId
			){
		chatRooms getRoom = chattingService.getRoom(roomId);
		return ResponseEntity.ok(getRoom);
	}
	
	@PostMapping("/room/{roomId}/read")
	public ResponseEntity<?> updateLastReadAt(
	        @PathVariable("roomId") long roomId,
	        @AuthenticationPrincipal UserDeptcode user
	) {
	    Long userNo = user.getUserNo();
	    log.info("userNo : {}",userNo);
	    int answer = chattingService.updateLastReadAt(roomId, userNo);
	    if(answer>0) {
		    return ResponseEntity.ok().build();
	    }else {
	    	return ResponseEntity.badRequest().build();
	    }
	}
}
