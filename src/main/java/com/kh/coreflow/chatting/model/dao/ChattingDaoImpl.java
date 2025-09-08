package com.kh.coreflow.chatting.model.dao;

import java.util.HashMap;
import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import com.kh.coreflow.chatting.model.dto.ChattingDto.chatMessages;
import com.kh.coreflow.chatting.model.dto.ChattingDto.chatProfile;
import com.kh.coreflow.chatting.model.dto.ChattingDto.chatRooms;
import com.kh.coreflow.chatting.model.dto.ChattingDto.userFavorite;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ChattingDaoImpl implements ChattingDao {
	
	private final SqlSessionTemplate session;
	
	@Override
	public List<chatProfile> getChatProfiles(int userNo) {
		return session.selectList("chat.getChatProfiles",userNo);
	}

	@Override
	public chatProfile getMyProfile(int userNo) {
		return session.selectOne("chat.getMyProfile",userNo);
	}

	@Override
	public int insertMyProfile(chatProfile myProfile) {
		return session.insert("chat.insertMyProfile",myProfile);
	}

	@Override
	public List<chatProfile> getFavoriteProfiles(int userNo) {
		return session.selectList("chat.getFavoriteProfiles",userNo);
	}

	@Override
	public int insertFavoriteProfiles(userFavorite favUser) {
		return session.insert("chat.insertFavoriteProfiles",favUser);
	}

	@Override
	public int deleteFavoriteProfiles(userFavorite favUser) {
		return session.delete("chat.deleteFavoriteProfiles",favUser);
	}

	@Override
	public int findPrivateChatIdByUserNo(HashMap<String, Integer> mappingUser) {
		String answer = session.selectOne("chat.findPrivateChatIdByUserNo",mappingUser);
		int returnvalue = 0;
		if(answer == null)
			returnvalue=-1;
		else
			returnvalue=Integer.parseInt(answer);
		return returnvalue;
	}
	
	@Override
	public chatRooms openPrivateChat(int roomId) {
		return session.selectOne("chat.openPrivateChat",roomId);
	}

	@Override
	public int makePrivateChat(chatRooms newChatRoom) {
		return session.insert("chat.makePrivateChat",newChatRoom);
	}

	@Override
	public int makePrivateChatJoin(HashMap<String, Integer> mappingUser) {
		return session.insert("chat.makePrivateChatJoin",mappingUser);
	}

	@Override
	public int insertMessage(chatMessages message) {
		return session.insert("chat.insertMessage",message);
	}

	@Override
	public List<chatMessages> getMessages(int roomId) {
		return session.selectList("chat.getMessages",roomId);
	}

	@Override
	public List<chatRooms> getmyChattingRooms(int userNo) {
		return session.selectList("chat.getMyChattingRooms",userNo);
	}

}
