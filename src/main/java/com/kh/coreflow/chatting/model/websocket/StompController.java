package com.kh.coreflow.chatting.model.websocket;

import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

import org.springframework.stereotype.Controller;

import com.kh.coreflow.chatting.model.dto.ChattingDto.chatMessages;
import com.kh.coreflow.chatting.model.service.ChattingService;
import com.kh.coreflow.model.dto.UserDto.User;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequiredArgsConstructor
public class StompController {
	
	private final ChattingService service;

    //들어오는 메시지를 처리
    @MessageMapping("/chat/enter/{roomNo}")
    //결과를 /topic/room/{roomNo}를 구독하는 모든 클라이언트에게 전송
    @SendTo("/topic/room/{roomNo}")
    public chatMessages handleEnter(
            @DestinationVariable int roomNo,
            chatMessages message // @Payload 생략 가능
    ) {
        message.setType(chatMessages.MessageType.ENTER);
        message.setMessageText(message.getUserName() + "님이 입장하셨습니다.");
        // ChatMessage 객체를 반환하면 @SendTo 경로로 자동 브로드캐스팅됩니다.
        return message;
    }

    @MessageMapping("/chat/exit/{roomNo}")
    @SendTo("/topic/room/{roomNo}")
    public chatMessages handleExit(
            @DestinationVariable int roomNo,
            chatMessages message
    ) {
        //service.exitChatRoom(message); // 서비스 로직 호출

        message.setType(chatMessages.MessageType.EXIT);
        message.setMessageText(message.getUserName() + "님이 퇴장하셨습니다.");

        return message;
    }
    
    //메시지 전송을 위한 컨트롤러
    @MessageMapping("/chat/message/{roomNo}")
    @SendTo("/topic/room/{roomNo}")
    public chatMessages handleMessage(
            @DestinationVariable int roomNo,
            @AuthenticationPrincipal User user,
            chatMessages message
    ) {
    	message.setUserName(user.getName());
    	message.setUserNo(user.getUserNo());
    	log.info("message : {}",message);
    	int result = service.insertMessage(message);
    	if(result >0)
    		return message;
    	else {
    		return null;
    	}
    }
}