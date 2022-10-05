package com.personal.typingracer.service.impl;

import com.personal.typingracer.entity.ActiveSessionDetails;
import com.personal.typingracer.entity.GameDetailsEntity;
import com.personal.typingracer.entity.Player;
import com.personal.typingracer.model.Content;
import com.personal.typingracer.model.enums.WebSocketMessageType;
import com.personal.typingracer.model.websocket.ContentWebSocketOutgoingMessage;
import com.personal.typingracer.repository.ActiveSessionDetailsRepository;
import com.personal.typingracer.repository.GamesDetailsRepository;
import com.personal.typingracer.service.ContentGenerator;
import com.personal.typingracer.service.SessionManager;
import com.personal.typingracer.service.WebSocketPublisher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * @author nikhilshinde on 28/09/22
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SessionManagerImpl implements SessionManager {

    private final GamesDetailsRepository gamesDetailsRepository;
    private final ActiveSessionDetailsRepository activeSessionDetailsRepository;
    private final WebSocketPublisher webSocketPublisher;
    private final ContentGenerator contentGenerator;

    @Value("${game.config.max-user-per-session}")
    private Integer maxUsersPerSession;

    /**
     * This method creates game for the new user. As of now the users per game is limited to 5.
     * Method will look for sessions which are not yet filled with all users and assign
     * current user to one of them, if no session is free then it will create new session
     * and assign user to it.
     * There are two databases, Mysql and Redis.
     * MySql Stores only details related to game such as GameID and Users Count.
     * Redis Stores Data for the active session of that game such as GameId and User IDs.
     *
     * @param principal : Username created by spring itself
     */
    @Override
    public void createNewGame(Principal principal) {
        try {
            Optional<GameDetailsEntity> optionalActiveSession = gamesDetailsRepository
                    .findTopByUserCountIsLessThan(maxUsersPerSession);

            GameDetailsEntity activeSession = optionalActiveSession.orElseGet(() -> GameDetailsEntity.builder()
                    .gameId(UUID.randomUUID().toString())
                    .build());

            activeSession.setUserCount(activeSession.getUserCount() + 1);

            gamesDetailsRepository.save(activeSession);

            String gameId = activeSession.getGameId();

            storeSession(gameId, principal);

            /*
            * As soon as game fills, message with content will be dispatched to every user in that game
            * */
            if (activeSession.getUserCount() == maxUsersPerSession){
                List<Player> players = getAllPlayersByGameId(gameId);
                Content content = contentGenerator.generateContent();
                players.forEach(player -> {
                    webSocketPublisher.publishStatusEvents(
                            ContentWebSocketOutgoingMessage.builder()
                                    .content(content)
                                    .type(WebSocketMessageType.CONTENT)
                                    .build()
                            , player.getUsername());
                });
            }
            log.info("User {} registered with game {}", principal, gameId);
        } catch (Exception e){
            log.info("Error while creating session for {}", principal);
            //TODO: Send error event on websocket for user, and retry again from front-end
        }

    }

    private void storeSession(String gameId, Principal username) {
        Optional<ActiveSessionDetails> optionalActiveSessionDetails = activeSessionDetailsRepository
                .getActiveSessionDetailsByGameId(gameId);

        ActiveSessionDetails activeSessionDetails = optionalActiveSessionDetails.orElseGet(() ->
                ActiveSessionDetails.builder().gameId(gameId).players(new ArrayList<>()).build()
        );

        activeSessionDetails.getPlayers().add(Player.builder().username(username).build());

        activeSessionDetailsRepository.saveOrUpdateActiveSession(activeSessionDetails);

    }

    /**
     * Method looks for active session with provided gameId and
     * returns all assigned players in that game
     *
     * @param gameId : gameId
     *
     * @return List<Player> :
     * */
    @Override
    public List<Player> getAllPlayersByGameId(String gameId) {
        Optional<ActiveSessionDetails> optionalActiveSessionDetails =
                activeSessionDetailsRepository.getActiveSessionDetailsByGameId(gameId);

        return optionalActiveSessionDetails.orElseGet(() -> optionalActiveSessionDetails.orElseGet(() ->
                ActiveSessionDetails.builder().gameId(gameId).players(List.of()).build()
        )).getPlayers();
    }
}
