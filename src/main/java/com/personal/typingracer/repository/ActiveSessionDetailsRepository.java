package com.personal.typingracer.repository;

import com.personal.typingracer.entity.ActiveSessionDetails;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author nikhilshinde on 01/10/22
 */
@Repository
public class ActiveSessionDetailsRepository {

    private final HashOperations<String, String, ActiveSessionDetails> hashOperations;

    public ActiveSessionDetailsRepository(RedisTemplate<String, ActiveSessionDetails> redisTemplate) {
        this.hashOperations = redisTemplate.opsForHash();
    }

    private static final String HASH_KEY = "ACTIVE_SESSIONS";

    public void saveOrUpdateActiveSession(ActiveSessionDetails activeSessionDetails) {
        hashOperations.put(HASH_KEY, activeSessionDetails.getGameId(), activeSessionDetails);
    }

    public Optional<ActiveSessionDetails> getActiveSessionDetailsByGameId(String gameId) {
        return Optional.ofNullable(hashOperations.get(HASH_KEY, gameId));
    }

}
