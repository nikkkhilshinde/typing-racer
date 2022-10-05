package com.personal.typingracer.service.impl;

import com.personal.typingracer.model.WebSocketOutgoingMessage;
import com.personal.typingracer.service.WebSocketPublisher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.security.Principal;

/**
 * @author nikhilshinde on 04/10/22
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class WebSocketPublisherImpl implements WebSocketPublisher {

    private final SimpMessagingTemplate simpMessagingTemplate;
    private final static String EVENTS_TOPIC = "/topic/events";
    private final static String ERRORS_TOPIC = "/topic/errors";

    /**
     * Method will be used by all classes to publish any normal event on websocket
     * Message will be sent to specific user on topic.
     *
     * @param message   : content
     * @param principal : username
     */
    @Override
    public void publishStatusEvents(WebSocketOutgoingMessage message, Principal principal) {
        simpMessagingTemplate.convertAndSendToUser(principal.getName(), EVENTS_TOPIC, message);
    }

    /**
     * Method will be used by all classes to publish any error event on websocket
     * Message will be sent to specific user on topic.
     *
     * @param message   : content
     * @param principal : username
     */
    @Override
    public void publishErrorEvents(WebSocketOutgoingMessage message, Principal principal) {
        simpMessagingTemplate.convertAndSendToUser(principal.getName(), ERRORS_TOPIC, message);
    }
}
