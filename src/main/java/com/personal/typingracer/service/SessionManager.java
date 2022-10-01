package com.personal.typingracer.service;

import com.personal.typingracer.model.User;

import java.util.List;

/**
 * @author nikhilshinde on 28/09/22
 */
public interface SessionManager {

    User addUser();

    void storeSession(String gameId, String sessionId);

    List<String> getAssociatedSessionsWithGame(String gameId);

}
