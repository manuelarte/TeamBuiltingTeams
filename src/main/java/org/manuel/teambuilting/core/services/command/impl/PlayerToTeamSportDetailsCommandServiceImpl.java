package org.manuel.teambuilting.core.services.command.impl;

import javax.inject.Inject;

import org.manuel.teambuilting.core.model.PlayerToTeamSportDetails;
import org.manuel.teambuilting.core.repositories.PlayerToTeamSportDetailsRepository;
import org.manuel.teambuilting.core.services.command.PlayerToTeamSportDetailsCommandService;
import org.springframework.stereotype.Service;

/**
 * @author Manuel Doncel Martos
 *
 */
@Service
class PlayerToTeamSportDetailsCommandServiceImpl extends AbstractCommandService<PlayerToTeamSportDetails, String, PlayerToTeamSportDetailsRepository> implements
	PlayerToTeamSportDetailsCommandService {

	@Inject
	public PlayerToTeamSportDetailsCommandServiceImpl(final PlayerToTeamSportDetailsRepository playerToTeamSportDetailsRepository) {
		super(playerToTeamSportDetailsRepository);
	}

}
