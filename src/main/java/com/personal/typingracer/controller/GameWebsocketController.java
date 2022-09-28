package com.personal.typingracer.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

/**
 * @author nikhilshinde on 28/09/22
 */
@Slf4j
@Controller
public class GameWebsocketController {

    @MessageMapping("/chat")
    public void received(Message<String> message) {
        log.info("Received message {}", message.getPayload());
    }
}
