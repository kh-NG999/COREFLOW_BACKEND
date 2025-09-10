package com.kh.coreflow.chatting.model.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import com.kh.coreflow.chatting.model.dto.ChattingDto.chatMessages;
import com.kh.coreflow.chatting.model.dto.ChattingDto.chatProfile;
import com.kh.coreflow.chatting.model.dto.ChattingDto.chatRooms;
import com.kh.coreflow.chatting.model.dto.ChattingDto.userFavorite;
import com.kh.coreflow.model.dto.UserDto.User;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Repository
@RequiredArgsConstructor
@Slf4j
public class ChattingDaoImpl implements ChattingDao {
	
	private final SqlSessionTemplate session;
	
	@Override
	public List<chatProfile> getChatProfiles(Long userNo) {
		return session.selectList("chat.getChatProfiles",userNo);
	}

	@Override
	public chatProfile getMyProfile(Long userNo) {
		return session.selectOne("chat.getMyProfile",userNo);
	}

	@Override
	public int insertMyProfile(chatProfile myProfile) {
		return session.insert("chat.insertMyProfile",myProfile);
	}

	@Override
	public List<chatProfile> getFavoriteProfiles(Long userNo) {
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
	public int makeChatRoom(chatRooms newChatRoom) {
		return session.insert("chat.makeChatRoom",newChatRoom);
	}

	@Override
	public int insertMessage(chatMessages message) {
		return session.insert("chat.insertMessage",message);
	}

	@Override
	public List<chatMessages> getMessages(Long roomId) {
		return session.selectList("chat.getMessages",roomId);
	}

	@Override
	public List<chatRooms> getmyChattingRooms(Long userNo) {
		return session.selectList("chat.getMyChattingRooms",userNo);
	}

	@Override
	public List<chatMessages> getLastMessagesForRooms(List<chatRooms> myRooms) {
		return session.selectList("chat.selectLastMessagesForRooms",myRooms);
	}

	@Override
	public Long findRoomByMember(List<Long> privateMember, String type) {
		Map<String, Object> params = new HashMap<>();
	    
	    params.put("userNos", privateMember);
	    params.put("userCount", privateMember.size());
	    params.put("type", type);
	    
	    return session.selectOne("chat.findRoomByMember", params);
	}

	@Override
	public chatRooms openChat(Long roomId) {
		return session.selectOne("chat.openChat",roomId);
	}

	@Override
	public int makeChatJoin(Long roomId, List<Long> chatRoomJoin) {
		Map<String, Object> params = new HashMap<>();
	    params.put("roomId", roomId);
	    params.put("userNos", chatRoomJoin);
	    log.info("list : {}",chatRoomJoin);
	    return session.insert("chat.insertChatRoomJoins", params);
	}

	@Override
	public User findUserByUserNo(Long userNo) {
		return session.selectOne("chat.findUserByUserNo",userNo);
	}

	@Override
	public chatRooms getRoom(Long roomId) {
		return session.selectOne("chat.getRoom",roomId);
	}

}
