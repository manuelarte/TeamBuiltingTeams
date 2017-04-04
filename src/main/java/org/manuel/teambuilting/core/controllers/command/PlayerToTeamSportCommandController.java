package org.manuel.teambuilting.core.controllers.command;

import javax.validation.Valid;

import org.manuel.teambuilting.core.model.PlayerToTeamSportDetails;
import org.manuel.teambuilting.core.services.command.PlayerToTeamSportDetailsCommandService;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/core/players/{playerId}/details")
@AllArgsConstructor
public class PlayerToTeamSportCommandController {

	private final PlayerToTeamSportDetailsCommandService playerToTeamSportDetailsService;

	@RequestMapping(method = RequestMethod.POST)
	public PlayerToTeamSportDetails savePlayerDetails(@PathVariable("playerId") final String playerId,
			@Valid @RequestBody final PlayerToTeamSportDetails playerToTeamSportDetails) {
		Assert.hasLength(playerId);
		Assert.isTrue(playerId.equals(playerToTeamSportDetails.getPlayerId()));
		return playerToTeamSportDetailsService.save(playerToTeamSportDetails);
	}

}
