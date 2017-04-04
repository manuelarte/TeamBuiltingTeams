package org.manuel.teambuilting.core.controllers;

import java.util.Collection;

import javax.inject.Inject;
import javax.validation.Valid;

import org.manuel.teambuilting.core.model.PlayerToTeam;
import org.manuel.teambuilting.core.services.command.PlayerToTeamCommandService;
import org.manuel.teambuilting.core.services.query.PlayerToTeamQueryService;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/core/players/{playerId}/teams")
public class PlayerToTeamController {

	private final PlayerToTeamQueryService playerToTeamQueryService;
	private final PlayerToTeamCommandService playerToTeamCommandService;

	@Inject
	public PlayerToTeamController(final PlayerToTeamQueryService playerToTeamQueryService, final PlayerToTeamCommandService playerToTeamCommandService) {
		this.playerToTeamQueryService = playerToTeamQueryService;
		this.playerToTeamCommandService = playerToTeamCommandService;
	}

	@RequestMapping(method = RequestMethod.GET)
	public Collection<PlayerToTeam> findPlayerHistory(@PathVariable("playerId") final String playerId) {
		Assert.notNull(playerId);
		return playerToTeamQueryService.findPlayerHistory(playerId);
	}

	@RequestMapping(method = RequestMethod.POST, produces = "application/json")
	public PlayerToTeam savePlayerToTeam(@PathVariable("playerId") final String playerId,
			@Valid @RequestBody final PlayerToTeam playerToTeam) {
		Assert.notNull(playerToTeam);
		Assert.isTrue(playerToTeam.getPlayerId().equals(playerId));
		return playerToTeamCommandService.save(playerToTeam);
	}

	@RequestMapping(value = "/{playerToTeamId}", method = RequestMethod.DELETE, produces = "application/json")
	public void deletePlayerToTeam(@PathVariable("playerId") final String playerId,
										   @PathVariable("playerToTeamId") final String playerToTeamId) {
		playerToTeamCommandService.delete(playerToTeamId);
	}

}
