package com.personal.typingracer.service;

import com.personal.typingracer.entity.Player;
import com.personal.typingracer.model.NewGameDto;

import java.util.List;

/**
 * @author nikhilshinde on 28/09/22
 */
public interface SessionManager {

    NewGameDto createNewGame();

    boolean storeSession(String gameId, String websocketSessionId);

    List<Player> getAllPlayersByGameId(String gameId);

}
