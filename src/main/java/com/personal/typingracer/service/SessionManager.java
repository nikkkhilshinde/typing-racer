package com.personal.typingracer.service;

import com.personal.typingracer.entity.Player;

import java.util.List;

/**
 * @author nikhilshinde on 28/09/22
 */
public interface SessionManager {

    void createNewGame(String username);

    List<Player> getAllPlayersByGameId(String gameId);

}
