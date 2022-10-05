package com.personal.typingracer.model.websocket;

import com.personal.typingracer.model.enums.WebSocketMessageType;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author nikhilshinde on 04/10/22
 */
@Getter
@AllArgsConstructor
public class BaseWebSocketOutgoingMessage<T> {
    private WebSocketMessageType messageType;
    private T data;
}
