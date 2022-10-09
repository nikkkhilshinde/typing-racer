package com.personal.typingracer.model.websocket;

import com.personal.typingracer.model.UserStatus;
import com.personal.typingracer.model.enums.WebSocketMessageType;
import lombok.Builder;

/**
 * @author nikhilshinde on 05/10/22
 */
public class StatusWebSocketOutgoingMessage extends BaseWebSocketOutgoingMessage<UserStatus>{

    @Builder
    public StatusWebSocketOutgoingMessage(WebSocketMessageType type, UserStatus userStatus) {
        super(type, userStatus);
    }
}

