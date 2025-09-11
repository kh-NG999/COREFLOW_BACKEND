package com.kh.coreflow.chatting.model.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

public class ChattingDto {
	
	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	@Builder
	public static class chatMessages{
		private Long messageId;
		private Long roomId;
		private Long userNo;
		private String messageText;
		private Date sentAt;
		private String isFile;

		// 클라이언트의 메시지 유형을 관리할 속성
		public enum MessageType{
			ENTER, EXIT, TALK
		}
		private MessageType type;
		
		private String userName;
	}
	
	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	@Builder
	public static class chatProfile{
		private Long userNo;
		private String userName;
		private String status;
	}
	
	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	@Builder
	public static class chatRoomJoin{
		private Long userNo;
		private Long roomId;
		private Date joinedAt;
		private Date lastReadAt;
	}
	
	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	@Builder
	public static class chatRooms{
		private Long roomId;
		private Long userNo;
		private String roomName;
		private String roomType;
		private String status;
		private Date createdAt;
		private chatMessages lastMessage;
		
		private int unreadCount;
	}
	
	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	@Builder
	public static class userFavorite{
		private Long favoriteId;
		private Long userNo;
		private Long favoriteUserNo;
	}
}
