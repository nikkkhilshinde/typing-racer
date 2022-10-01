package com.personal.typingracer.service.impl;

import com.personal.typingracer.entity.GameDetailsEntity;
import com.personal.typingracer.model.NewGameDto;
import com.personal.typingracer.repository.GamesDetailsRepository;
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

    //Maintains associated user-ids per active game
    //gameId1 -> [user1, user2, user3, user4, user5]
    private final Map<String, List<String>> activeSessionCookieMap = new HashMap<>();

    private final GamesDetailsRepository gamesDetailsRepository;

    @Value("${game.config.max-user-per-session}")
    private Integer maxUsersPerSession;

    @Override
    public NewGameDto createNewGame() {
        Optional<GameDetailsEntity> optionalActiveSession = gamesDetailsRepository
                .getActiveSessionByUserCountIsLessThan(maxUsersPerSession);

        GameDetailsEntity activeSession = optionalActiveSession.orElseGet(() -> GameDetailsEntity.builder()
                .gameId(UUID.randomUUID().toString())
                .build());

        activeSession.setUserCount(activeSession.getUserCount() + 1);

        gamesDetailsRepository.save(activeSession);

        String sessionId = activeSession.getGameId();

        if (!activeSessionCookieMap.containsKey(sessionId)) {
            activeSessionCookieMap.put(sessionId, new ArrayList<>());
        }

        //Generate random userId and store it in map to verify afterwards that
        //the websocket message is coming from legitimate user
        String userId = UUID.randomUUID().toString();
        activeSessionCookieMap.get(sessionId).add(userId);

        return NewGameDto.builder()
                .userId(userId)
                .gameId(sessionId)
                .build();
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
