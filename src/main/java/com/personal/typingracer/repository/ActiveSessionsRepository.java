package com.personal.typingracer.repository;

import com.personal.typingracer.entity.ActiveSession;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * @author nikhilshinde on 26/09/22
 */
@Repository
public interface ActiveSessionsRepository extends CrudRepository<ActiveSession, Long> {
}
