package com.personal.typingracer.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * @author nikhilshinde on 26/09/22
 */
@Entity
@Table(name = "active_sessions")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ActiveSession {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String sessionId;

    private boolean isFull;

    private int userCount;
}
