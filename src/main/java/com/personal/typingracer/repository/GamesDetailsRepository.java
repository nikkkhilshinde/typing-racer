package com.personal.typingracer.repository;

import com.personal.typingracer.entity.GameDetailsEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author nikhilshinde on 26/09/22
 */
@Repository
public interface GamesDetailsRepository extends CrudRepository<GameDetailsEntity, Long> {

    Optional<GameDetailsEntity> getActiveSessionByUserCountIsLessThan(int maxCount);

}
