/**
 * 
 */
package org.manuel.teambuilting.core.services.query;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

import javax.inject.Inject;

import org.manuel.teambuilting.core.model.Player;
import org.manuel.teambuilting.core.model.PlayerToTeam;
import org.manuel.teambuilting.core.repositories.PlayerRepository;
import org.manuel.teambuilting.core.repositories.PlayerToTeamRepository;
import org.springframework.stereotype.Service;

/**
 * @author Manuel Doncel Martos
 *
 */
@Service
public class PlayerToTeamQueryService {

	private final PlayerToTeamRepository playerToTeamRepository;
	private final PlayerRepository playerRepository;

	@Inject
	public PlayerToTeamQueryService(final PlayerToTeamRepository playerToTeamRepository,
			final PlayerRepository playerRepository) {
		this.playerToTeamRepository = playerToTeamRepository;
		this.playerRepository = playerRepository;
	}

	public Set<Player> getPlayersFor(final String teamId, final LocalDate time) {
		final Collection<PlayerToTeam> playersForTeam = playerToTeamRepository
				.findByToDateAfterOrToDateIsNullAndTeamId(time, teamId);
		return playersForTeam.stream()
				.map(playerId -> playerRepository.findOne(playerId.getPlayerId())).collect(Collectors.toSet());
	}

	public Collection<PlayerToTeam> findPlayerHistory(final String playerId) {
		return playerToTeamRepository.findByPlayerId(playerId);
	}
}
