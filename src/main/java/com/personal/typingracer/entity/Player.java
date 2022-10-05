package com.personal.typingracer.entity;

import lombok.Builder;
import lombok.Getter;

import java.io.Serializable;
import java.security.Principal;

/**
 * @author nikhilshinde on 01/10/22
 */
@Getter
@Builder
public class Player implements Serializable {
    private Principal username;
}
