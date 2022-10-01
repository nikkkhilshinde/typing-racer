package com.personal.typingracer.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * @author nikhilshinde on 29/09/22
 */
@Getter
@Setter
@Builder
public class User {
    private String gameId;
    private String userId;
}
