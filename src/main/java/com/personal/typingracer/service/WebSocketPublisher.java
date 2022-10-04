package com.personal.typingracer.service;

import com.personal.typingracer.model.WebSocketOutgoingMessage;

import java.security.Principal;

/**
 * @author nikhilshinde on 04/10/22
 */
public interface WebSocketPublisher {

    void publishStatusEvents(WebSocketOutgoingMessage message, Principal principal);
    void publishErrorEvents(WebSocketOutgoingMessage message, Principal principal);
}
