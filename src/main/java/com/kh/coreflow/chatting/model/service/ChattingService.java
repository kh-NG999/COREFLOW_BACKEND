package com.kh.coreflow.chatting.model.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

	int insertMessage(chatMessages message);

	List<chatMessages> getMessages(int roomId);

	List<chatRooms> getmyChattingRooms(int userNo);

	int makeChat(int userNo, Map<String, Object> newChatParam, String string);

	chatRooms openChat(List<Integer> privateMember);

}
