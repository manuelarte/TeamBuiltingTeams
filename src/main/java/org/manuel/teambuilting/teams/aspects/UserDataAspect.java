package org.manuel.teambuilting.teams.aspects;

import com.auth0.authentication.result.UserProfile;
import com.auth0.spring.security.api.Auth0JWTToken;

import javax.inject.Inject;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.manuel.teambuilting.teams.config.Auth0Client;
import org.manuel.teambuilting.teams.model.Team;
import org.manuel.teambuilting.teams.model.UserData;
import org.manuel.teambuilting.teams.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

/**
 * @author manuel.doncel.martos
 * @since 9-1-2017
 */
@Aspect
@Component
public class UserDataAspect {

	private final UserService userService;
	private final Auth0Client auth0Client;

	@Inject
	public UserDataAspect(final UserService userService, final Auth0Client auth0Client) {
		this.userService = userService;
		this.auth0Client = auth0Client;
	}

	@AfterReturning(
		pointcut="@annotation(org.manuel.teambuilting.teams.aspects.UserDataSave)",
		returning="retVal")
	public void saveEntityToUserData(final JoinPoint call, Object retVal) {
		final Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		final UserProfile user = auth0Client.getUser((Auth0JWTToken) auth);
		final UserData userData = userService.getOrCreateUserData(user.getId());
		if (retVal instanceof Team) {
			userData.addTeamAdminByUser(((Team) retVal).getId());
			userService.update(userData);
		}

	}

}
