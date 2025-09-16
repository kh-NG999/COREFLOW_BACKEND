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
import com.kh.coreflow.chatting.model.dto.ChattingDto.chatProfileDetail;
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
	public List<chatProfile> getChatProfiles(Long userNo) {
		return chattingDao.getChatProfiles(userNo);
	}

	@Override
	public chatProfile getMyProfile(Long userNo) {
		chatProfile myProfile = chattingDao.getMyProfile(userNo);
		System.out.println(userNo);
		if(myProfile==null) {
			User myInfo = chattingDao.findUserByUserNo(userNo);
			myProfile = new chatProfile().builder()
					.userNo(userNo)
					.userName(myInfo.getUserName())
					.build();
			int result = chattingDao.insertMyProfile(myProfile);
		}
		return chattingDao.getMyProfile(userNo);
	}

	@Override
	public List<chatProfile> getFavoriteProfiles(Long userNo) {
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
	public Long makeChat(Long userNo, Map<String, Object> newChatParam, String type) {
		Long roomId = 0L;
		chatRooms newChatRoom = new chatRooms();
		newChatRoom.setRoomType(type);
		newChatRoom.setUserNo(userNo);
		if(type.equals("PRIVATE")) {
			Long partnerNo = (Long)newChatParam.get("partner");
			User partner = chattingDao.findUserByUserNo(partnerNo);
			newChatRoom.setRoomName(partner.getUserName() + "님과의 채팅");
			newChatParam.remove("partner");
		}else {
			newChatRoom.setRoomName((String)newChatParam.get("roomName"));
		}
		
		int answer = chattingDao.makeChatRoom(newChatRoom);
		if(answer>0) {
			roomId = Long.valueOf(newChatRoom.getRoomId());
			answer = chattingDao.makeChatJoin(roomId,(List<Long>)newChatParam.get("participantUserNos"));
			return roomId;
		}
		return roomId;
	}

	@Override
	public int insertMessage(chatMessages message) {
		return chattingDao.insertMessage(message);
		
	}

	@Override
	public List<chatMessages> getMessages(Long roomId) {
		return chattingDao.getMessages(roomId);
	}

	@Override
	public List<chatRooms> getmyChattingRooms(Long userNo) {
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
	public chatRooms openChat(List<Long> privateMember,String type) {
		Long roomId = chattingDao.findRoomByMember(privateMember,type);
		if(roomId==null)
			return null;
		return chattingDao.openChat(roomId);
	}

	@Override
	public chatRooms getRoom(Long roomId) {
		return chattingDao.getRoom(roomId);
	}

	@Override
	public List<Long> getParticipantUserNos(Long roomId) {
		return chattingDao.getParticipantUserNos(roomId);
	}

	@Override
	public chatRooms getUpdatedChatRoomInfo(Long userNo, Long roomId, chatMessages message) {
		return chattingDao.getUpdatedChatRoomInfo(userNo, roomId,message);
	}

	@Override
	public int updateLastReadAt(long roomId, Long userNo) {
		return chattingDao.updateLastReadAt(roomId,userNo);
	}
	
	@Override
	public int updateState(String status, Long userNo) {
		return chattingDao.updateState(status,userNo);
	}

	@Override
	public List<chatProfile> findChatProfiles(Long userNo, String query) {
		return chattingDao.findChatProfiles(userNo,query);
	}

	@Override
	public List<chatProfile> getRoomUsers(Long roomId) {
		return chattingDao.getRoomUsers(roomId);
	}

	@Override
	public int setJoinUser(Map<String, Object> getParam) {
		return chattingDao.setJoinUser(getParam);
	}

	@Override
	public chatProfileDetail getProfileDetail(Long userNo) {
		return chattingDao.getProfileDetail(userNo);
	}

	@Override
	public int changeMessage(chatMessages message) {
		return chattingDao.changeMessage(message);
	}

}
