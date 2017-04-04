/**
 * 
 */
package org.manuel.teambuilting.core.services.command.impl;

import java.util.Collection;
import java.util.stream.Collectors;

import org.manuel.teambuilting.core.exceptions.ValidationRuntimeException;
import org.manuel.teambuilting.core.model.PlayerToTeam;
import org.manuel.teambuilting.core.model.TimeSlice;
import org.manuel.teambuilting.core.repositories.PlayerToTeamRepository;
import org.manuel.teambuilting.core.services.command.PlayerToTeamCommandService;
import org.springframework.stereotype.Service;

/**
 * @author Manuel Doncel Martos
 *
 */
@Service
class PlayerToTeamCommandServiceImpl extends AbstractCommandService<PlayerToTeam, String, PlayerToTeamRepository> implements
	PlayerToTeamCommandService {

	public PlayerToTeamCommandServiceImpl(final PlayerToTeamRepository repository) {
		super(repository);
	}

	@Override
	void beforeSave(final PlayerToTeam playerToTeam) {
		final Collection<PlayerToTeam> historyOfThePlayerInTheTeamOverlapped = repository
			.findByPlayerIdAndTeamId(playerToTeam.getPlayerId(), playerToTeam.getTeamId()).stream().filter(entry -> !entry.getId().equals(playerToTeam.getId()) && isOverlapping(playerToTeam, entry)).collect(Collectors.toList());
		if (!historyOfThePlayerInTheTeamOverlapped.isEmpty()) {
			throw new ValidationRuntimeException("", "Entry overlapped", "Entry overlapped");
		}
	}

	private <T extends TimeSlice> boolean isOverlapping(final T entryOne, final T entryTwo) {
		if (entryOne.getFromDate().equals(entryTwo.getFromDate())) {
			return true;
		}
		else if (entryOne.getFromDate().after(entryTwo.getFromDate())) {
			return isOverlapping(entryTwo, entryOne);
		} else {
			final boolean entryTwoFromDateBetweenEntryOneDates = entryTwo.getToDate() == null || entryTwo.getFromDate().after(entryOne.getFromDate());
			return entryTwoFromDateBetweenEntryOneDates;
		}
	}
}
