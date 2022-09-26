package com.personal.typingracer.entity;

import lombok.Data;
import org.springframework.data.redis.core.RedisHash;

/**
 * @author nikhilshinde on 26/09/22
 */
@RedisHash("ActiveGame")
@Data
public class ActiveGame {
}
