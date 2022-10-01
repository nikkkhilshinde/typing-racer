package com.personal.typingracer.controller;

import com.personal.typingracer.model.WebSocketIncomingMessage;
import com.personal.typingracer.service.MessageProcessingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.security.Principal;

/**
 * @author nikhilshinde on 28/09/22
 */
@Slf4j
@Controller
@RequiredArgsConstructor
public class WebsocketController {

    private final SimpMessagingTemplate simpMessagingTemplate;
    private final MessageProcessingService messageProcessingService;

    @MessageMapping("/game")
    public void handleMessage(Message<WebSocketIncomingMessage> payload,
                              Principal principal) {

        messageProcessingService.handleMessage(payload.getPayload(), principal);
        //simpMessagingTemplate.convertAndSendToUser(principal.getName(), "/topic/messages", "payload");
    }
}
