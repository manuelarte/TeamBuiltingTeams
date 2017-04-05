/**
 * 
 */
package org.manuel.teambuilting.core.repositories;

import java.time.LocalDate;
import java.util.Collection;

import org.manuel.teambuilting.core.model.PlayerToTeam;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Manuel Doncel Martos
 *
 */
@Repository
public interface PlayerToTeamRepository extends PlayerDependentRepository<PlayerToTeam, String>, MongoRepository<PlayerToTeam, String> {

	/**
	 * Look for all the players that have played in the team whose id is teamId
	 * and they stopped played after the parameter end date
	 * 
	 * @param date
	 * @param teamId
	 * @return
	 */
	Collection<PlayerToTeam> findByToDateAfterOrToDateIsNullAndTeamId(LocalDate date, String teamId);

	Collection<PlayerToTeam> findByPlayerIdAndTeamId(String playerId, String teamId);

}
