package com.personal.typingracer.service.impl;

import com.personal.typingracer.entity.ActiveSession;
import com.personal.typingracer.model.User;
import com.personal.typingracer.repository.ActiveSessionsRepository;
import com.personal.typingracer.service.SessionManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author nikhilshinde on 28/09/22
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SessionManagerImpl implements SessionManager {

    private final Map<String, List<String>> activeGames = new HashMap<>();

    private final Map<String, List<String>> activeSessionCookieMap = new HashMap<>();

    private final ActiveSessionsRepository activeSessionsRepository;

    @Value("${game.config.max-user-per-session}")
    private Integer maxUsersPerSession;

    private static final int INITIAL_USER_COUNT = 1;

    @Override
    public User addUser() {
        Optional<ActiveSession> optionalActiveSession = activeSessionsRepository
                .getActiveSessionByUserCountIsLessThan(maxUsersPerSession);

        ActiveSession activeSession = optionalActiveSession.orElseGet(() -> ActiveSession.builder()
                .sessionId(UUID.randomUUID().toString())
                .build());

        activeSession.setUserCount(activeSession.getUserCount() + 1);
        if (activeSession.getUserCount() == maxUsersPerSession) {
            activeSession.setFull(Boolean.TRUE);
        }

        activeSessionsRepository.save(activeSession);

        String sessionId = activeSession.getSessionId();

        if (!activeSessionCookieMap.containsKey(sessionId)) {
            activeSessionCookieMap.put(sessionId, new ArrayList<>());
        }
        String userId = UUID.randomUUID().toString();
        activeSessionCookieMap.get(sessionId).add(userId);

        return User.builder().userId(userId).gameId(sessionId).build();
    }

    @Override
    public void storeSession(String gameId, String sessionId) {
        activeGames.get(gameId).add(sessionId);
    }

    @Override
    public List<String> getAssociatedSessionsWithGame(String gameId) {
        return activeGames.get(gameId);
    }
}
