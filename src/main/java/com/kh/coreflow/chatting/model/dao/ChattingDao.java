package com.kh.coreflow.chatting.model.dao;

import java.util.HashMap;
import java.util.List;

import com.kh.coreflow.chatting.model.dto.ChattingDto.chatMessages;
import com.kh.coreflow.chatting.model.dto.ChattingDto.chatProfile;
import com.kh.coreflow.chatting.model.dto.ChattingDto.chatRooms;
import com.kh.coreflow.chatting.model.dto.ChattingDto.userFavorite;

public interface ChattingDao {

	List<chatProfile> getChatProfiles(int userNo);

	chatProfile getMyProfile(int userNo);

	List<chatProfile> getFavoriteProfiles(int userNo);

	int insertFavoriteProfiles(userFavorite favUser);

	int deleteFavoriteProfiles(userFavorite favUser);

	chatRooms openPrivateChat(int roomId);

	int makePrivateChat(chatRooms newChatRoom);

	int findPrivateChatIdByUserNo(HashMap<String, Integer> mappingUser);

	int makePrivateChatJoin(HashMap<String, Integer> mappingUser);

	int insertMyProfile(chatProfile myProfile);

	int insertMessage(chatMessages message);

}
