/**
 * 
 */
package org.manuel.teambuilting.core.services;

import org.manuel.teambuilting.core.exceptions.ValidationRuntimeException;
import org.manuel.teambuilting.core.model.Player;
import org.manuel.teambuilting.core.model.PlayerToTeam;
import org.manuel.teambuilting.core.model.TimeSlice;
import org.manuel.teambuilting.core.repositories.PlayerRepository;
import org.manuel.teambuilting.core.repositories.PlayerToTeamRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author Manuel Doncel Martos
 *
 */
@Service
public class PlayerToTeamService {

	private final PlayerToTeamRepository playerToTeamRepository;
	private final PlayerRepository playerRepository;

	@Inject
	public PlayerToTeamService(final PlayerToTeamRepository playerToTeamRepository,
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

	@PreAuthorize("hasAuthority('user') or hasAuthority('admin')")
	public PlayerToTeam savePlayerToTeam(final PlayerToTeam playerToTeam) {
		final Collection<PlayerToTeam> historyOfThePlayerInTheTeamOverlapped = playerToTeamRepository
			.findByPlayerIdAndTeamId(playerToTeam.getPlayerId(), playerToTeam.getTeamId()).stream().filter(entry -> !entry.getId().equals(playerToTeam.getId()) && isOverlapping(playerToTeam, entry)).collect(Collectors.toList());
        if (!historyOfThePlayerInTheTeamOverlapped.isEmpty()) {
            throw new ValidationRuntimeException("", "Entry overlapped", "Entry overlapped");
        }
		return playerToTeamRepository.save(playerToTeam);
	}

	@PreAuthorize("hasAuthority('user') or hasAuthority('admin')")
	public void deletePlayerToTeam(final String playerToTeamId) {
		playerToTeamRepository.delete(playerToTeamId);
    }

	private <T extends TimeSlice> boolean isOverlapping(final T entryOne, final T entryTwo) {
        final boolean entryOneInsideEntryTwo = entryOne.getFromDate().after(entryTwo.getFromDate()) &&  entryOne.getToDate().before(entryTwo.getToDate());
	    final boolean fromDateBetweenEntryOneDates = entryTwo.getFromDate().after(entryOne.getFromDate()) && entryTwo.getFromDate().before(entryOne.getToDate()) && entryTwo.getFromDate().before(entryOne.getToDate());
		final boolean toDateBetweenEntryOneDates = entryTwo.getToDate().after(entryOne.getFromDate()) && entryTwo.getToDate().before(entryOne.getToDate());
		return entryOneInsideEntryTwo || fromDateBetweenEntryOneDates || toDateBetweenEntryOneDates;
	}
}
