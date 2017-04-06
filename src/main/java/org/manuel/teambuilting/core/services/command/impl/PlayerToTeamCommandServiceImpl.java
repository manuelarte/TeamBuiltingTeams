/**
 * 
 */
package org.manuel.teambuilting.core.services.command.impl;

import org.manuel.teambuilting.core.exceptions.ErrorCode;
import org.manuel.teambuilting.core.exceptions.ValidationRuntimeException;
import org.manuel.teambuilting.core.model.PlayerToTeam;
import org.manuel.teambuilting.core.repositories.PlayerToTeamRepository;
import org.manuel.teambuilting.core.services.command.PlayerToTeamCommandService;
import org.manuel.teambuilting.core.util.Util;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * @author Manuel Doncel Martos
 *
 */
@Service
class PlayerToTeamCommandServiceImpl extends AbstractCommandService<PlayerToTeam, String, PlayerToTeamRepository> implements
	PlayerToTeamCommandService {

	private final Util util;

	public PlayerToTeamCommandServiceImpl(final PlayerToTeamRepository repository, Util util) {
		super(repository);
		this.util = util;
	}

	@Override
	void beforeSave(final PlayerToTeam playerToTeam) {
		final Collection<PlayerToTeam> historyOfThePlayerInTheTeamOverlapped = repository
			.findByPlayerIdAndTeamId(playerToTeam.getPlayerId(), playerToTeam.getTeamId())
				.stream().filter(overlapped(playerToTeam)).collect(Collectors.toList());
		if (!historyOfThePlayerInTheTeamOverlapped.isEmpty()) {
			throw new ValidationRuntimeException(ErrorCode.ENTRY_OVERLAPS, playerToTeam);
		}
	}

	private Predicate<PlayerToTeam> overlapped(final PlayerToTeam beforeSaveEntity) {
		return (entry) -> !entry.getId().equals(beforeSaveEntity.getId())
				&& util.isOverlapping(beforeSaveEntity, entry);
	}

}
