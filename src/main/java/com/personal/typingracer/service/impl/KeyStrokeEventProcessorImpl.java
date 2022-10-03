package com.personal.typingracer.service.impl;

import com.personal.typingracer.model.KeyStrokeEvent;
import com.personal.typingracer.service.KeyStrokeEventProcessor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author nikhilshinde on 01/10/22
 */
@Service
@Slf4j
public class KeyStrokeEventProcessorImpl implements KeyStrokeEventProcessor {

    @Override
    public void processKeyStroke(KeyStrokeEvent event, String username) {
        log.info("");
    }
}
