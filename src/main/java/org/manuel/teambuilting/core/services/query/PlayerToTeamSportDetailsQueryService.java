package org.manuel.teambuilting.core.services.query;

import java.util.Optional;

import org.manuel.teambuilting.core.model.PlayerToTeamSportDetails;

/**
 * @author Manuel Doncel Martos
 *
 */
public interface PlayerToTeamSportDetailsQueryService extends BaseQueryService<PlayerToTeamSportDetails, String>, PlayerDependentQueryService<PlayerToTeamSportDetails, String> {

	Optional<PlayerToTeamSportDetails> findPlayerDetailsForSport(String playerId, String sport);

}
