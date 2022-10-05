package com.personal.typingracer.service.impl;

import com.personal.typingracer.service.SessionManager;
import com.personal.typingracer.service.UserRegistrationService;
import com.personal.typingracer.service.WebSocketPublisher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.security.Principal;

/**
 * @author nikhilshinde on 04/10/22
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class UserRegistrationServiceImpl implements UserRegistrationService {

    private final SessionManager sessionManager;
    private final WebSocketPublisher webSocketPublisher;

    /**
     * Method assign random user to one particular game which has not yet started
     *
     * @param principal : username
     * */
    @Override
    public void registerUser(Principal principal) {
        sessionManager.createNewGame(principal);
    }
}
