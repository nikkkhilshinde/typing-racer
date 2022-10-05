package com.personal.typingracer.service;

import com.personal.typingracer.entity.Player;

import java.security.Principal;
import java.util.List;

/**
 * @author nikhilshinde on 28/09/22
 */
public interface SessionManager {

    void createNewGame(Principal principal);

    List<Player> getAllPlayersByGameId(String gameId);

}
