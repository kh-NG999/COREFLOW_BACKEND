package com.kh.coreflow.chatting.model.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

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
	public int makeChat(int userNo, Map<String, Object> newChatParam, String type) {
		chatRooms newChatRoom = new chatRooms();
		newChatRoom.setRoomType(type);
		newChatRoom.setUserNo(userNo);
		if(type.equals("PRIVATE")) {
			int partnerNo = (int)newChatParam.get("partner");
			User partner = authDao.findUserByUserNo(partnerNo);
			newChatRoom.setRoomName(partner.getName() + "님과의 채팅");
			newChatParam.remove("partner");
		}else {
			newChatRoom.setRoomName((String)newChatParam.get("roomName"));
		}
		
		int answer = chattingDao.makeChatRoom(newChatRoom);
		if(answer>0) {
			int roomId = Long.valueOf(newChatRoom.getRoomId()).intValue();
			answer = chattingDao.makeChatJoin(roomId,(List<Integer>)newChatParam.get("participantUserNos"));
		}
		return answer;
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
		List<chatRooms> myRooms = chattingDao.getmyChattingRooms(userNo);

        if (myRooms != null && !myRooms.isEmpty()) {
            List<chatMessages> lastMessages = chattingDao.getLastMessagesForRooms(myRooms);
            Map<Long, chatMessages> lastMessageMap = lastMessages.stream().collect(Collectors.toMap(chatMessages::getRoomId, Function.identity()));

            myRooms.forEach(room -> {
                chatMessages lastMsg = lastMessageMap.get(room.getRoomId());
                if (lastMsg != null) {
                    room.setLastMessage(lastMsg);
                }
            
            });
        }
        
        return myRooms;
	}

	@Override
	@Transactional
	public chatRooms openChat(List<Integer> privateMember) {
		int roomId = chattingDao.findRoomByMember(privateMember);
		if(roomId==-1)
			return null;
		return chattingDao.openChat(roomId);
	}

}
