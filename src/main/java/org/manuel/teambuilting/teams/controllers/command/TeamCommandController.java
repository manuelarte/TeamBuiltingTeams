/**
 * 
 */
package org.manuel.teambuilting.teams.controllers.command;

import com.auth0.spring.security.api.authentication.AuthenticationJsonWebToken;
import lombok.AllArgsConstructor;
import org.manuel.teambuilting.teams.model.Team;
import org.manuel.teambuilting.teams.services.command.TeamCommandService;
import org.manuel.teambuilting.teams.services.geocoding.TeamGeocodingService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Optional;

/**
 * @author Manuel Doncel Martos
 *
 */
@RestController
@RequestMapping("/teams")
@AllArgsConstructor
public class TeamCommandController {
	
	private final TeamCommandService teamCommandService;
	private final TeamGeocodingService teamGeocodingService;

	@RequestMapping(method = RequestMethod.POST, produces = "application/json")
	public Team saveTeam(@Valid @RequestBody final Team team) {
		final Team saved = teamCommandService.save(team);
		if (Optional.ofNullable(saved.getLocation()).isPresent()) {
			teamGeocodingService.asyncReq(saved);
		}
		return saved;
    }

	/**
	 *  Simple illustration only
	 */
	private void adminChecks(final AuthenticationJsonWebToken principal) {
		for(final GrantedAuthority grantedAuthority: principal.getAuthorities()) {
			final String authority = grantedAuthority.getAuthority();
			if (("ROLE_ADMIN".equals(authority))) {
				// just a simple callout to demonstrate role based authorization at service level
				// non-Admin user would be rejected trying to call this service
			}
		}
	}

}