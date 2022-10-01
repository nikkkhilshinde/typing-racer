package com.personal.typingracer.entity;

import org.springframework.data.redis.core.RedisHash;

import java.util.List;

/**
 * @author nikhilshinde on 01/10/22
 */
@RedisHash("ActiveSession")
public class ActiveSessionDetails {

    private String gameId;
    private List<Player> players;
}

