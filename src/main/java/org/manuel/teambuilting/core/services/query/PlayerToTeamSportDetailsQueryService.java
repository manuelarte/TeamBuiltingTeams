package org.manuel.teambuilting.core.services.query;

import java.util.Optional;
import java.util.Set;

import org.manuel.teambuilting.core.model.PlayerToTeamSportDetails;

/**
 * @author Manuel Doncel Martos
 *
 */
public interface PlayerToTeamSportDetailsQueryService extends BaseQueryService<PlayerToTeamSportDetails, String>{

	Set<PlayerToTeamSportDetails> findPlayerDetails(String playerId);

	Optional<PlayerToTeamSportDetails> findPlayerDetailsForSport(String playerId, String sport);

}
