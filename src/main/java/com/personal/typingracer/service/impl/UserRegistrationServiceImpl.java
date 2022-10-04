package com.personal.typingracer.service.impl;

import com.personal.typingracer.service.SessionManager;
import com.personal.typingracer.service.UserRegistrationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author nikhilshinde on 04/10/22
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class UserRegistrationServiceImpl implements UserRegistrationService {

    private final SessionManager sessionManager;

    @Override
    public void registerUser(String gameId, String username) {
        sessionManager.createNewGame(username);
    }
}
