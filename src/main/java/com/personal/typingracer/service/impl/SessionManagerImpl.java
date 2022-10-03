package com.personal.typingracer.service.impl;

import com.personal.typingracer.entity.ActiveSessionDetails;
import com.personal.typingracer.entity.GameDetailsEntity;
import com.personal.typingracer.entity.Player;
import com.personal.typingracer.model.NewGameDto;
import com.personal.typingracer.repository.ActiveSessionDetailsRepository;
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
    //private final Map<String, List<String>> activeSessionCookieMap = new HashMap<>();

    private final GamesDetailsRepository gamesDetailsRepository;
    private final ActiveSessionDetailsRepository activeSessionDetailsRepository;

    @Value("${game.config.max-user-per-session}")
    private Integer maxUsersPerSession;

    @Override
    public NewGameDto createNewGame() {
        Optional<GameDetailsEntity> optionalActiveSession = gamesDetailsRepository
                .findTopByUserCountIsLessThan(maxUsersPerSession);

        GameDetailsEntity activeSession = optionalActiveSession.orElseGet(() -> GameDetailsEntity.builder()
                .gameId(UUID.randomUUID().toString())
                .build());

        activeSession.setUserCount(activeSession.getUserCount() + 1);

        gamesDetailsRepository.save(activeSession);

        String sessionId = activeSession.getGameId();

        return NewGameDto.builder()
                .gameId(sessionId)
                .build();
    }

    @Override
    public boolean storeSession(String gameId, String username) {
        Optional<ActiveSessionDetails> optionalActiveSessionDetails = activeSessionDetailsRepository
                .getActiveSessionDetailsByGameId(gameId);

        ActiveSessionDetails activeSessionDetails = optionalActiveSessionDetails.orElseGet(() ->
                ActiveSessionDetails.builder().gameId(gameId).players(new ArrayList<>()).build()
        );

        activeSessionDetails.getPlayers().add(Player.builder().username(username).build());

        try {
            activeSessionDetailsRepository.saveOrUpdateActiveSession(activeSessionDetails);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    @Override
    public List<Player> getAllPlayersByGameId(String gameId) {
        Optional<ActiveSessionDetails> optionalActiveSessionDetails =
                activeSessionDetailsRepository.getActiveSessionDetailsByGameId(gameId);

        return optionalActiveSessionDetails.orElseGet(() -> optionalActiveSessionDetails.orElseGet(() ->
                ActiveSessionDetails.builder().gameId(gameId).players(List.of()).build()
        )).getPlayers();
    }
}
