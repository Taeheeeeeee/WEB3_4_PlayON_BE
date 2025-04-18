package com.ll.playon.domain.chat.controller;

import com.ll.playon.domain.chat.service.ChatMessageService;
import com.ll.playon.domain.member.entity.Member;
import com.ll.playon.global.webSocket.WebSocketUserContext;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Controller;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class ChatMessageController {
    private final WebSocketUserContext webSocketUserContext;
    private final ChatMessageService chatMessageService;

    @MessageMapping("/chat.send/{partyId}")
    public void handleMessage(@DestinationVariable long partyId, @Payload String message, Principal principal) {
        Member sender = this.webSocketUserContext.getActor(principal);

        this.chatMessageService.broadcastMessage(sender, partyId, message);
    }
}
