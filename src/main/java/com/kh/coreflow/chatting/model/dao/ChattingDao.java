package com.kh.coreflow.chatting.model.dao;

import java.util.HashMap;
import java.util.List;

import com.kh.coreflow.chatting.model.dto.ChattingDto.chatMessages;
import com.kh.coreflow.chatting.model.dto.ChattingDto.chatProfile;
import com.kh.coreflow.chatting.model.dto.ChattingDto.chatRooms;
import com.kh.coreflow.chatting.model.dto.ChattingDto.userFavorite;
import com.kh.coreflow.model.dto.UserDto.User;

public interface ChattingDao {

	List<chatProfile> getChatProfiles(Long userNo);

	chatProfile getMyProfile(Long userNo);

	List<chatProfile> getFavoriteProfiles(Long userNo);

	int insertFavoriteProfiles(userFavorite favUser);

	int deleteFavoriteProfiles(userFavorite favUser);

	int makeChatRoom(chatRooms newChatRoom);

	int insertMyProfile(chatProfile myProfile);

	int insertMessage(chatMessages message);

	List<chatMessages> getMessages(Long roomId);

	List<chatRooms> getmyChattingRooms(Long userNo);

	List<chatMessages> getLastMessagesForRooms(List<chatRooms> myRooms);

	Long findRoomByMember(List<Long> privateMember, String type);

	chatRooms openChat(Long roomId);

	int makeChatJoin(Long roomId, List<Long> list);

	User findUserByUserNo(Long userNo);

	chatRooms getRoom(Long roomId);

}
