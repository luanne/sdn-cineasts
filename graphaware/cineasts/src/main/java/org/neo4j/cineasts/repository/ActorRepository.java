package org.neo4j.cineasts.repository;

import org.neo4j.cineasts.domain.Actor;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.stereotype.Repository;

/**
 * @author mh
 * @since 02.04.11
 */

public interface ActorRepository extends GraphRepository<Actor> {
    Actor findById(String id);   //TODO unsupported in Milestone 1
}
