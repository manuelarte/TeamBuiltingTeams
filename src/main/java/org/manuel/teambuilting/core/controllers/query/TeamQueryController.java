/**
 * 
 */
package org.manuel.teambuilting.core.controllers.query;

import java.time.LocalDate;
import java.util.Set;

import javax.inject.Inject;

import org.manuel.teambuilting.core.config.Auth0Client;
import org.manuel.teambuilting.core.model.Player;
import org.manuel.teambuilting.core.model.Team;
import org.manuel.teambuilting.core.services.query.PlayerToTeamQueryService;
import org.manuel.teambuilting.core.services.query.TeamQueryService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Manuel Doncel Martos
 *
 */
@RestController
@RequestMapping("/core/teams")
public class TeamQueryController extends AbstractQueryController<Team, String, TeamQueryService>{

	private final PlayerToTeamQueryService playerToTeamQueryService;

	@Inject
	public TeamQueryController(final TeamQueryService teamQueryService, final PlayerToTeamQueryService playerToTeamQueryService, final Auth0Client auth0Client) {
		super(teamQueryService);
		this.playerToTeamQueryService = playerToTeamQueryService;
	}

	@RequestMapping(method = RequestMethod.GET)
	public Page<Team> findTeamBy(@PageableDefault(page = 0, size = 20) final Pageable pageable,
		@RequestParam(value = "sport", defaultValue = "") final String sport,
			@RequestParam(value = "name", defaultValue = "") final String name) {
		return queryService.findTeamBy(pageable, sport, name);
	}

	@RequestMapping(path = "/{teamId}/players", method = RequestMethod.GET)
	public Set<Player> getPlayersForTeam(@PathVariable("teamId") final String teamId,
			@RequestParam(value = "date", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") final LocalDate date) {
		Assert.hasLength(teamId);
		return playerToTeamQueryService.getPlayersFor(teamId, date);
	}

}
