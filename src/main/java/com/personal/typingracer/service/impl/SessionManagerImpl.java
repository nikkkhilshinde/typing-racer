package com.personal.typingracer.service.impl;

import com.personal.typingracer.entity.ActiveSession;
import com.personal.typingracer.repository.ActiveSessionsRepository;
import com.personal.typingracer.service.SessionManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

/**
 * @author nikhilshinde on 28/09/22
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SessionManagerImpl implements SessionManager {

    private final ActiveSessionsRepository activeSessionsRepository;

    @Value("${game.config.max-user-per-session}")
    private Integer maxUsersPerSession;

    private static final int INITIAL_USER_COUNT = 1;

    @Override
    public String addUser() {
        Optional<ActiveSession> optionalActiveSession = activeSessionsRepository
                .getActiveSessionByUserCountIsLessThan(maxUsersPerSession);

        if (optionalActiveSession.isPresent()) {
            ActiveSession activeSession = optionalActiveSession.get();
            activeSession.setUserCount(activeSession.getUserCount() + 1);
            if (activeSession.getUserCount() == maxUsersPerSession) {
                activeSession.setFull(Boolean.TRUE);
            }
            activeSessionsRepository.save(activeSession);
            return optionalActiveSession.get().getSessionId().toString();
        } else {
            ActiveSession activeSession = new ActiveSession();
            activeSession.setSessionId(UUID.randomUUID());
            activeSession.setFull(false);
            activeSession.setUserCount(INITIAL_USER_COUNT);
            activeSessionsRepository.save(activeSession);
            return activeSession.getSessionId().toString();
        }
    }
}
