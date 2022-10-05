package com.personal.typingracer.config;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

/**
 * @author nikhilshinde on 28/09/22
 */
@Configuration
@EnableWebSocketMessageBroker
@Slf4j
@AllArgsConstructor
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
    /**
     * Websocket configuration. Handshake Handler used to assign random ID to every session
     */
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/game")
                .setHandshakeHandler(new CustomWebSocketHandshakeHandler())
                .setAllowedOrigins("*");
        registry.addEndpoint("/game")
                .setHandshakeHandler(new CustomWebSocketHandshakeHandler())
                .setAllowedOriginPatterns("*")
                .withSockJS();
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/topic");
        config.setApplicationDestinationPrefixes("/app");
    }
}
