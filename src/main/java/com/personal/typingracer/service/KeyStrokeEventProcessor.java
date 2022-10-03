package com.personal.typingracer.service;

import com.personal.typingracer.model.KeyStrokeEvent;

/**
 * @author nikhilshinde on 01/10/22
 */
public interface KeyStrokeEventProcessor {

    void processKeyStroke(KeyStrokeEvent event, String username);
}
