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

    @Override
    public void publishStatusEvents(WebSocketOutgoingMessage message, Principal principal) {
        simpMessagingTemplate.convertAndSendToUser(principal.getName(), EVENTS_TOPIC, message);
    }

    @Override
    public void publishErrorEvents(WebSocketOutgoingMessage message, Principal principal) {
        simpMessagingTemplate.convertAndSendToUser(principal.getName(), ERRORS_TOPIC, message);
    }
}
