package com.kh.coreflow.chatting.model.websocket;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.user.SimpUser;
import org.springframework.messaging.simp.user.SimpUserRegistry;
import org.springframework.stereotype.Controller;

import com.kh.coreflow.chatting.model.dto.ChattingDto.SignalMessage;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequiredArgsConstructor
public class WebRTCController {
	
	// 특정 사용자에게 메시지를 보낼 때 사용하는 Spring WebSocket 메시징 템플릿
    private final SimpMessagingTemplate messagingTemplate;
    private final SimpUserRegistry userRegistry; // 사용자 접속 상태 확인용

    /**
     * WebRTC 시그널링 메시지를 중개하는 핸들러
     * @MessageMapping("/webrtc/signal")
     * - 클라이언트가 "/app/webrtc/signal" 주소로 메시지를 보내면 이 메서드가 처리합니다.
     */
    @MessageMapping("/webrtc/signal")
    public void sendSignal(@Payload SignalMessage signalMessage) {
        String recipientId = String.valueOf(signalMessage.getTo());
        SimpUser user = userRegistry.getUser(recipientId);
        boolean isReadyForCall = false;
        // 1. 사용자가 온라인 상태인지 먼저 확인합니다.
        if (user != null) {
            log.info("sender : {}, sessions : {}",String.valueOf(signalMessage.getFrom()),user.getSessions());
            // 2. 해당 사용자의 모든 구독 정보를 확인하여,
            //    WebRTC 시그널링 채널('/user/queue/webrtc')을 구독 중인지 체크합니다.
        	 isReadyForCall = user.getSessions().stream()
        	            .flatMap(session -> session.getSubscriptions().stream())
        	            .anyMatch(sub -> sub.getDestination() != null && sub.getDestination().endsWith("/queue/webrtc"));
        }

        // 3. 사용자가 화상채팅을 받을 준비가 된 경우에만 시그널을 전달합니다.
        if (isReadyForCall) {
            log.info("sender : {}, User {} is ready for a call. Forwarding signal: {}", signalMessage.getFrom(), recipientId, signalMessage.getType());
            messagingTemplate.convertAndSendToUser(
                recipientId,
                "/queue/webrtc",
                signalMessage
            );
        } else {
            // 4. 오프라인이거나, WebRTC 채널을 구독하고 있지 않은 경우
            log.info("sender : {}, User {} is offline or not ready for a call.", signalMessage.getFrom(),  recipientId);
            SignalMessage offlineResponse = new SignalMessage("user-offline", signalMessage.getTo(), signalMessage.getFrom(), null);
            messagingTemplate.convertAndSendToUser(
                String.valueOf(signalMessage.getFrom()),
                "/queue/webrtc",
                offlineResponse
            );
        }
    }
}
