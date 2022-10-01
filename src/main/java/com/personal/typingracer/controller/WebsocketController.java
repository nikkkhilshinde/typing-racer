package com.personal.typingracer.controller;

import com.personal.typingracer.service.SessionManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.Header;
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

    private final SessionManager sessionManager;
    private final SimpMessagingTemplate simpMessagingTemplate;

    @MessageMapping("/game/register")
    public void registerUserSession(Principal principal, @Header("gameId") String gameId) {

        boolean isSuccessfullyRegistered = sessionManager.storeSession(gameId, principal.getName());
        if (isSuccessfullyRegistered) {
            log.info("User '{}' registered with game '{}'", principal.getName(), gameId);
        } else {
            log.info("Error occurred while registering user '{}' with game '{}'", principal.getName(), gameId);
        }
        //simpMessagingTemplate.convertAndSendToUser(principal.getName(), "/topic/messages", "payload");
    }
}
