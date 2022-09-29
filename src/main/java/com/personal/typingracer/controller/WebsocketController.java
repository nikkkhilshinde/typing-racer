package com.personal.typingracer.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import java.security.Principal;

/**
 * @author nikhilshinde on 28/09/22
 */
@Slf4j
@Controller
public class WebsocketController {

    @MessageMapping("/chat")
    @SendTo("/topic/messages")
    public String received(Message<String> message, Principal principal) {
        log.info("Received message {}", message.getPayload());
        return "hello";
    }
}
