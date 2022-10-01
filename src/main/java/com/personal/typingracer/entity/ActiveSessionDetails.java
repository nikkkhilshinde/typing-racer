package com.personal.typingracer.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.AccessType;
import org.springframework.data.redis.core.RedisHash;

import javax.persistence.Id;
import java.io.Serializable;
import java.util.List;

/**
 * @author nikhilshinde on 01/10/22
 */
@RedisHash
@Builder
@Getter
public class ActiveSessionDetails implements Serializable {
    @Id
    private String gameId;
    private List<Player> players;
}

