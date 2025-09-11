package com.kh.coreflow.chatting.model.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.kh.coreflow.chatting.model.dto.ChattingDto.chatMessages;
import com.kh.coreflow.chatting.model.dto.ChattingDto.chatProfile;
import com.kh.coreflow.chatting.model.dto.ChattingDto.chatRooms;
import com.kh.coreflow.chatting.model.dto.ChattingDto.userFavorite;

public interface ChattingService {

	List<chatProfile> getChatProfiles(Long userNo);

	chatProfile getMyProfile(Long userNo);

	List<chatProfile> getFavoriteProfiles(Long userNo);

	int insertFavoriteProfiles(userFavorite favUser);

	int deleteFavoriteProfiles(userFavorite favUser);

	int insertMessage(chatMessages message);

	List<chatMessages> getMessages(Long roomId);

	List<chatRooms> getmyChattingRooms(Long userNo);

	Long makeChat(Long userNo, Map<String, Object> newChatParam, String string);

	chatRooms openChat(List<Long> privateMember, String string);

	chatRooms getRoom(Long roomId);

	List<Long> getParticipantUserNos(Long roomId);

	chatRooms getUpdatedChatRoomInfo(Long userNo, Long roomId, chatMessages message);

	int updateLastReadAt(long roomId, Long userNo);

}
