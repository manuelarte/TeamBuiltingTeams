package org.manuel.teambuilting.core.controllers.query;

import java.util.Collection;
import java.util.Optional;

import javax.inject.Inject;

import org.manuel.teambuilting.core.exceptions.ErrorCode;
import org.manuel.teambuilting.core.exceptions.ValidationRuntimeException;
import org.manuel.teambuilting.core.model.PlayerToTeamSportDetails;
import org.manuel.teambuilting.core.services.query.PlayerToTeamSportDetailsQueryService;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/core/players/{playerId}/details")
public class PlayerToTeamSportDetailsQueryController {

	private final PlayerToTeamSportDetailsQueryService queryService;

	@Inject
	public PlayerToTeamSportDetailsQueryController(final PlayerToTeamSportDetailsQueryService queryService) {
		this.queryService = queryService;
	}

	@RequestMapping(method = RequestMethod.GET)
	public Collection<PlayerToTeamSportDetails> findAllPlayerSportDetails(@PathVariable("playerId") final String playerId) {
		Assert.hasLength(playerId);
		return queryService.findByPlayerId(playerId);
	}

	@RequestMapping(path = "/{sport}", method = RequestMethod.GET)
	public ResponseEntity<PlayerToTeamSportDetails> findPlayerSportDetailsForSport(@PathVariable("playerId") final String playerId, @PathVariable("sport") final String sport) {
		Assert.hasLength(playerId);
		Assert.hasLength(sport);
		final Optional<PlayerToTeamSportDetails> playerToTeamSportDetails = queryService.findPlayerDetailsForSport(playerId, sport);
		if (playerToTeamSportDetails.isPresent()) {
			return ResponseEntity.ok(playerToTeamSportDetails.get());
		}
		throw new ValidationRuntimeException(ErrorCode.PLAYER_DETAIL_FOR_SPORT_NOT_FOUND, playerId, sport);
	}

}
