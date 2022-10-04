package com.personal.typingracer.service.impl;

import com.personal.typingracer.exception.WebSocketMessageFormatException;
import com.personal.typingracer.model.KeyStrokeEvent;
import com.personal.typingracer.model.WebSocketIncomingMessage;
import com.personal.typingracer.model.WebSocketOutgoingMessage;
import com.personal.typingracer.model.enums.WebSocketMessageType;
import com.personal.typingracer.service.KeyStrokeEventProcessor;
import com.personal.typingracer.service.MessageProcessingService;
import com.personal.typingracer.service.UserRegistrationService;
import com.personal.typingracer.service.WebSocketPublisher;
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

    private final KeyStrokeEventProcessor keyStrokeEventProcessor;
    private final UserRegistrationService userRegistrationService;
    private final WebSocketPublisher webSocketPublisher;

    @Override
    public void handleMessage(WebSocketIncomingMessage message, Principal principal) {
        if (!validateMessage(message)) {
            log.error("Unable to process message {} due to invalid parameters", message);
            webSocketPublisher.publishErrorEvents(new WebSocketOutgoingMessage(WebSocketMessageType.ERROR), principal);
            return;
        }

        switch (message.getMessageType()) {

            case REGISTER:
                userRegistrationService.registerUser(message.getGameId(), principal.getName());
                break;

            case KEY_STROKE:
                keyStrokeEventProcessor.processKeyStroke((KeyStrokeEvent) message.getData(), principal.getName());
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
}
