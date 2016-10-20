/**
 * 
 */
package org.manuel.teambuilting.model.repository;

import java.time.LocalDate;
import java.util.Collection;

import org.manuel.teambuilting.model.PlayerToTeam;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Manuel Doncel Martos
 *
 */
@Repository
public interface PlayerToTeamRepository extends MongoRepository<PlayerToTeam, String> {

	PlayerToTeam findByTeamId(String teamId);

	Collection<PlayerToTeam> findByEndDateAfterOrEndDateIsNullAndTeamId(LocalDate date, String teamId);

}