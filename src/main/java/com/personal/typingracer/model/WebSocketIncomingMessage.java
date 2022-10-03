package com.personal.typingracer.model;

import com.personal.typingracer.model.enums.WebSocketMessageType;
import lombok.Getter;

/**
 * @author nikhilshinde on 01/10/22
 */
@Getter
public class WebSocketIncomingMessage {
    private WebSocketMessageType messageType;
    private String gameId;
    private Object data;
}
