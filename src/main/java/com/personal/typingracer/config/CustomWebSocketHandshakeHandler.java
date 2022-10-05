package com.personal.typingracer.config;

import com.sun.security.auth.UserPrincipal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

import java.security.Principal;
import java.util.Map;
import java.util.UUID;

/**
 * @author nikhilshinde on 03/10/22
 */
@Slf4j
public class CustomWebSocketHandshakeHandler extends DefaultHandshakeHandler {

    /**
     * As currently we are allowing users to connect randomly without
     * creating an account, so we have to assign every user a random id.
     * for differentiating them.
     * determineUser assigns every websocket session a random UUID and creates
     * principal. This principal is used by spring session to differentiate users/sessions
     */
    @Override
    protected Principal determineUser(ServerHttpRequest request,
                                      WebSocketHandler wsHandler,
                                      Map<String, Object> attributes) {
        final String randomId = UUID.randomUUID().toString();
        log.info("User with ID '{}' connected", randomId);
        return new UserPrincipal(randomId);
    }
}
