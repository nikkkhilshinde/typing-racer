package com.personal.typingracer.model;

import com.personal.typingracer.model.enums.WebSocketMessageType;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author nikhilshinde on 04/10/22
 */
@AllArgsConstructor
@Data
public class WebSocketOutgoingMessage {
    private WebSocketMessageType messageType;
}
