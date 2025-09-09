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

	int makeChatRoom(chatRooms newChatRoom);

	int insertMyProfile(chatProfile myProfile);

	int insertMessage(chatMessages message);

	List<chatMessages> getMessages(int roomId);

	List<chatRooms> getmyChattingRooms(int userNo);

	List<chatMessages> getLastMessagesForRooms(List<chatRooms> myRooms);

	int findRoomByMember(List<Integer> privateMember);

	chatRooms openChat(int roomId);

	int makeChatJoin(int roomId, List<Integer> list);

}
