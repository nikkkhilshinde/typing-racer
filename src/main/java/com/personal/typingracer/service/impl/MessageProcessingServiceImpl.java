package com.personal.typingracer.service.impl;

import com.personal.typingracer.exception.WebSocketMessageFormatException;
import com.personal.typingracer.model.WebSocketIncomingMessage;
import com.personal.typingracer.service.MessageProcessingService;
import com.personal.typingracer.service.SessionManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.security.Principal;

/**
 * @author nikhilshinde on 01/10/22
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class MessageProcessingServiceImpl implements MessageProcessingService {

    private final SessionManager sessionManager;

    @Override
    public void handleMessage(WebSocketIncomingMessage message, Principal principal) {
        if (!validateMessage(message)) {
            log.error("Unable to process message {} due to invalid parameters", message);
            return;
        }

        switch (message.getMessageType()) {
            case REGISTER:
                boolean isRegisteredSuccessfully = registerUser(message.getGameId(), principal.getName());
                log.info("Status: User registration '{}' with game '{}' : {}",
                        principal.getName(), message.getGameId(), isRegisteredSuccessfully);
                break;

            default:
                log.info("Cannot process message due to invalid message type {} from user {}",
                        message.getMessageType(), principal.getName());
                break;
        }
    }

    private boolean validateMessage(WebSocketIncomingMessage message) {
        if (message.getGameId() != null && message.getMessageType() != null && message.getGameId().isEmpty()){
            throw new WebSocketMessageFormatException("Invalid message parameters " + message);
        }

        return true;
    }

    private boolean registerUser(String gameId, String username) {
        return sessionManager.storeSession(gameId, username);
    }
}
