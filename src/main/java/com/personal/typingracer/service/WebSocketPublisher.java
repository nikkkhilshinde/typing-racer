package com.personal.typingracer.service;

import com.personal.typingracer.model.websocket.BaseWebSocketOutgoingMessage;

import java.security.Principal;

/**
 * @author nikhilshinde on 04/10/22
 */
public interface WebSocketPublisher {

    void publishStatusEvents(BaseWebSocketOutgoingMessage<?> message, Principal principal);
    void publishErrorEvents(BaseWebSocketOutgoingMessage<?> message, Principal principal);
}
