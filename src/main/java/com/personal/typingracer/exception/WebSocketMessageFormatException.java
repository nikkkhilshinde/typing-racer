package com.personal.typingracer.exception;

/**
 * @author nikhilshinde on 26/09/22
 */
public class WebSocketMessageFormatException extends RuntimeException{
    public WebSocketMessageFormatException(String message) {
        super(message);
    }
}
