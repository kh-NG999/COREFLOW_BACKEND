package com.kh.coreflow.chatting.model.service;

import java.util.HashMap;
import java.util.List;

import com.kh.coreflow.chatting.model.dto.ChattingDto.chatMessages;
import com.kh.coreflow.chatting.model.dto.ChattingDto.chatProfile;
import com.kh.coreflow.chatting.model.dto.ChattingDto.chatRooms;
import com.kh.coreflow.chatting.model.dto.ChattingDto.userFavorite;

public interface ChattingService {

	List<chatProfile> getChatProfiles(int userNo);

	chatProfile getMyProfile(int userNo);

	List<chatProfile> getFavoriteProfiles(int userNo);

	int insertFavoriteProfiles(userFavorite favUser);

	int deleteFavoriteProfiles(userFavorite favUser);

	chatRooms openPrivateChat(HashMap<String, Integer> mappingUser);

	chatRooms makePrivateChat(HashMap<String, Integer> mappingUser);

	int insertMessage(chatMessages message);

}
