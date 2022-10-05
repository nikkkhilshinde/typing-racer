package com.personal.typingracer.controller;

import com.personal.typingracer.model.websocket.WebSocketIncomingMessage;
import com.personal.typingracer.service.MessageProcessingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

import java.security.Principal;

/**
 * @author nikhilshinde on 28/09/22
 */
@Slf4j
@Controller
@RequiredArgsConstructor
public class WebsocketController {

    private final MessageProcessingService messageProcessingService;

    /**
     * Websocket endpoint /app/game {/app is prefix for evert message mapping}
     * Every user has to send message on this topic
     * Spring session will assign principal (username)
     * this is how it is assigned src/main/java/com/personal/typingracer/config/CustomWebSocketHandshakeHandler.java
     * @param payload : message
     * @param principal : username
     */
    @MessageMapping("/game")
    public void handleMessage(Message<WebSocketIncomingMessage> payload,
                              Principal principal) {

        messageProcessingService.handleMessage(payload.getPayload(), principal);
    }
}
