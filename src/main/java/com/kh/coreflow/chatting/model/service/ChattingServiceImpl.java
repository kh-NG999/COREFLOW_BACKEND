package com.kh.coreflow.chatting.model.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import com.kh.coreflow.model.dto.UserDto.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kh.coreflow.chatting.model.dao.ChattingDao;
import com.kh.coreflow.chatting.model.dto.ChattingDto.chatMessages;
import com.kh.coreflow.chatting.model.dto.ChattingDto.chatProfile;
import com.kh.coreflow.chatting.model.dto.ChattingDto.chatRooms;
import com.kh.coreflow.chatting.model.dto.ChattingDto.userFavorite;
import com.kh.coreflow.model.dao.AuthDao;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class ChattingServiceImpl implements ChattingService {
	
	@Autowired
	private ChattingDao chattingDao;
	
	@Autowired
	private AuthDao authDao;
	
	@Override
	public List<chatProfile> getChatProfiles(int userNo) {
		return chattingDao.getChatProfiles(userNo);
	}

	@Override
	public chatProfile getMyProfile(int userNo) {
		chatProfile myProfile = chattingDao.getMyProfile(userNo);
		if(myProfile==null) {
			User myInfo = authDao.findUserByUserNo(userNo);
			myProfile = new chatProfile().builder()
					.userNo(userNo)
					.userName(myInfo.getName())
					.build();
			int result = chattingDao.insertMyProfile(myProfile);
		}
		return chattingDao.getMyProfile(userNo);
	}

	@Override
	public List<chatProfile> getFavoriteProfiles(int userNo) {
		return chattingDao.getFavoriteProfiles(userNo);
	}

	@Override
	public int insertFavoriteProfiles(userFavorite favUser) {
		return chattingDao.insertFavoriteProfiles(favUser);
	}

	@Override
	public int deleteFavoriteProfiles(userFavorite favUser) {
		return chattingDao.deleteFavoriteProfiles(favUser);
	}

	@Override
	@Transactional
	public chatRooms openPrivateChat(HashMap<String, Integer> mappingUser) {
		int roomId = chattingDao.findPrivateChatIdByUserNo(mappingUser);
		if(roomId==-1)
			return null;
		return chattingDao.openPrivateChat(roomId);
		
	}

	@Override
	@Transactional
	public chatRooms makePrivateChat(HashMap<String, Integer> mappingUser) {
		chatRooms newChatRoom = new chatRooms();
		int partnerNo = mappingUser.get("partnerNo");
		User partner = authDao.findUserByUserNo(partnerNo);
		log.info("partner : {}", partner);
		newChatRoom.setRoomName(partner.getName() + "님과의 채팅");
		newChatRoom.setRoomType("PRIVATE");
		newChatRoom.setUserNo(mappingUser.get("userNo"));
		int answer = chattingDao.makePrivateChat(newChatRoom);
		if(answer>0) {
			mappingUser.put("roomId", Long.valueOf(newChatRoom.getRoomId()).intValue());
			answer = chattingDao.makePrivateChatJoin(mappingUser);
			if(answer>0) {
				log.info("info : {}",mappingUser);
				int roomId = chattingDao.findPrivateChatIdByUserNo(mappingUser);
				return chattingDao.openPrivateChat(roomId);
			}else
				return null;
		}
		else
			return null;
	}

	@Override
	public int insertMessage(chatMessages message) {
		return chattingDao.insertMessage(message);
		
	}

	@Override
	public List<chatMessages> getMessages(int roomId) {
		return chattingDao.getMessages(roomId);
	}

	@Override
	public List<chatRooms> getmyChattingRooms(int userNo) {
		return chattingDao.getmyChattingRooms(userNo);
	}

}
