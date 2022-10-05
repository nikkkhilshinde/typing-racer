package com.personal.typingracer.service;

import com.personal.typingracer.model.websocket.WebSocketIncomingMessage;

import java.security.Principal;

/**
 * @author nikhilshinde on 01/10/22
 */
public interface MessageProcessingService {

    void handleMessage(WebSocketIncomingMessage payload, Principal principal);
}
