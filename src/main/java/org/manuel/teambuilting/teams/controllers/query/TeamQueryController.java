/**
 * 
 */
package org.manuel.teambuilting.teams.controllers.query;

import javax.inject.Inject;

import org.manuel.teambuilting.teams.model.Team;
import org.manuel.teambuilting.teams.services.query.TeamQueryService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Manuel Doncel Martos
 *
 */
@RestController
@RequestMapping("/teams")
public class TeamQueryController extends AbstractQueryController<Team, String, TeamQueryService>{

	@Inject
	public TeamQueryController(final TeamQueryService teamQueryService) {
		super(teamQueryService);
	}

	@RequestMapping(method = RequestMethod.GET)
	public Page<Team> findTeamBy(@PageableDefault(page = 0, size = 20) final Pageable pageable,
		@RequestParam(value = "sport", defaultValue = "") final String sport,
			@RequestParam(value = "name", defaultValue = "") final String name) {
		return queryService.findTeamBy(pageable, sport, name);
	}

}
