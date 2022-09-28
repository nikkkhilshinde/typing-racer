package com.personal.typingracer.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.UUID;

/**
 * @author nikhilshinde on 26/09/22
 */
@Entity
@Table(name = "active_sessions")
@Data
public class ActiveSession {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private UUID sessionId;

    private boolean isFull;

    private int userCount;
}
