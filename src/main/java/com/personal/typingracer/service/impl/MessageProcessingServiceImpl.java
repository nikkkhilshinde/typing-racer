package com.personal.typingracer.service.impl;

import com.personal.typingracer.exception.WebSocketMessageFormatException;
import com.personal.typingracer.model.KeyStrokeEvent;
import com.personal.typingracer.model.enums.WebSocketMessageType;
import com.personal.typingracer.model.websocket.BaseWebSocketOutgoingMessage;
import com.personal.typingracer.model.websocket.WebSocketIncomingMessage;
import com.personal.typingracer.service.*;
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
    private final ContentGenerator contentGenerator;

    /**
     * Every incoming message from client on websocket will be handled by this method
     * Message will be handled according to its type. For the first time user will send
     * message with type REGISTER and for subsequent type would be KEY_STROKES.
     * Event will be published for every KEY_STROKE including back-space/delete.
     *
     * @param message   : its base class and it can be extended to create another message type
     * @param principal : username
     */
    @Override
    public void handleMessage(WebSocketIncomingMessage message, Principal principal) {
        if (!validateMessage(message)) {
            log.error("Unable to process message {} due to invalid parameters", message);
            webSocketPublisher.publishErrorEvents(new BaseWebSocketOutgoingMessage<>(WebSocketMessageType.ERROR, null), principal);
            return;
        }

        switch (message.getMessageType()) {

            case REGISTER:
                userRegistrationService.registerUser(principal);
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
