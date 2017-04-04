package org.manuel.teambuilting.core.services.query.impl;

import java.util.Optional;
import java.util.Set;

import javax.inject.Inject;

import org.manuel.teambuilting.core.model.PlayerToTeamSportDetails;
import org.manuel.teambuilting.core.repositories.PlayerToTeamSportDetailsRepository;
import org.manuel.teambuilting.core.services.query.PlayerToTeamSportDetailsQueryService;
import org.springframework.stereotype.Service;

/**
 * @author Manuel Doncel Martos
 *
 */
@Service
class PlayerToTeamSportDetailsQueryServiceImpl extends AbstractQueryService<PlayerToTeamSportDetails, String, PlayerToTeamSportDetailsRepository> implements
	PlayerToTeamSportDetailsQueryService {

	@Inject
	public PlayerToTeamSportDetailsQueryServiceImpl(final PlayerToTeamSportDetailsRepository playerToTeamSportDetailsRepository) {
		super(playerToTeamSportDetailsRepository);
	}

	public Set<PlayerToTeamSportDetails> findPlayerDetails(final String playerId) {
		return repository.findByPlayerId(playerId);
	}

	public Optional<PlayerToTeamSportDetails> findPlayerDetailsForSport(final String playerId, final String sport) {
		return Optional.ofNullable(repository.findByPlayerIdAndSportIgnoringCase(playerId, sport));
	}

}
