package org.manuel.teambuilting.teams.repositories;

import org.manuel.teambuilting.teams.model.Team;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Manuel Doncel Martos
 */
@Repository
public interface TeamRepository extends MongoRepository<Team, String> {

	Page<Team> findBySportLikeIgnoreCaseAndNameLikeIgnoreCase(Pageable pageable, String sport, String name);

}
