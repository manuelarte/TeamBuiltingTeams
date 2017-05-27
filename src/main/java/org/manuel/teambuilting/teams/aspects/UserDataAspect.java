package org.manuel.teambuilting.teams.aspects;

import com.auth0.Auth0User;
import lombok.AllArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.manuel.teambuilting.teams.model.Team;
import org.manuel.teambuilting.teams.model.UserData;
import org.manuel.teambuilting.teams.services.UserService;
import org.manuel.teambuilting.teams.util.Util;
import org.springframework.stereotype.Component;

/**
 * @author manuel.doncel.martos
 * @since 9-1-2017
 */
@Aspect
@Component
@AllArgsConstructor
public class UserDataAspect {

	private final UserService userService;
	private final Util util;

	@AfterReturning(
		pointcut="@annotation(org.manuel.teambuilting.teams.aspects.UserDataSave)",
		returning="retVal")
	public void saveEntityToUserData(final JoinPoint call, Object retVal) {
		final Auth0User user = util.getUserProfile().get();
		final UserData userData = userService.getOrCreateUserData(user.getUserId());
		if (retVal instanceof Team) {
			userData.addTeamAdminByUser(((Team) retVal).getId());
			userService.update(userData);
		}

	}

}
