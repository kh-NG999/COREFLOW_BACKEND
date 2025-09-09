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
		private long messageId;
		private long roomId;
		private long userNo;
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
		private long userNo;
		private String userName;
		private String status;
	}
	
	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	@Builder
	public static class chatRoomJoin{
		private long userNo;
		private long roomId;
		private Date joinedAt;
		private Date lastReadAt;
	}
	
	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	@Builder
	public static class chatRooms{
		private long roomId;
		private long userNo;
		private String roomName;
		private String roomType;
		private String status;
		private Date createdAt;
		private chatMessages lastMessage;
	}
	
	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	@Builder
	public static class userFavorite{
		private long favoriteId;
		private long userNo;
		private long favoriteUserNo;
	}
}
