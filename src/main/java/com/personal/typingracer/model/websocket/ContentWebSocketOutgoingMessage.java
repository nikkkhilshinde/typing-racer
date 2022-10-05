package com.personal.typingracer.model.websocket;

import com.personal.typingracer.model.Content;
import com.personal.typingracer.model.enums.WebSocketMessageType;
import lombok.Builder;
import lombok.Getter;

/**
 * @author nikhilshinde on 05/10/22
 */
@Getter
public class ContentWebSocketOutgoingMessage extends BaseWebSocketOutgoingMessage<Content>{

    @Builder
    public ContentWebSocketOutgoingMessage(WebSocketMessageType type, Content content) {
        super(type, content);
    }
}
