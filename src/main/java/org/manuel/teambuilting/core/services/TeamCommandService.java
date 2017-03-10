/**
 * 
 */
package org.manuel.teambuilting.core.services;

import com.auth0.authentication.result.UserProfile;
import com.auth0.spring.security.api.Auth0JWTToken;

import java.util.Date;

import javax.inject.Inject;

import org.manuel.teambuilting.core.aspects.UserDataSave;
import org.manuel.teambuilting.core.config.Auth0Client;
import org.manuel.teambuilting.core.messages.TeamCreatedMessage;
import org.manuel.teambuilting.core.model.Team;
import org.manuel.teambuilting.core.repositories.TeamRepository;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

/**
 * @author Manuel Doncel Martos
 *
 */
@Service
public class TeamCommandService {

	@Value("${messaging.event.amqp.exchange.name}")
	private String teamExchangeName;

	@Value("${messaging.event.amqp.team.queue.team-created-routing-key}")
	private String teamCreatedRoutingKey;

	private final TeamRepository teamRepository;
	private final RabbitTemplate rabbitTemplate;
	private final Auth0Client auth0Client;

	@Inject
	public TeamCommandService(final TeamRepository teamRepository, final RabbitTemplate rabbitTemplate,
							  final Auth0Client auth0Client) {
		this.teamRepository = teamRepository;
		this.rabbitTemplate = rabbitTemplate;
		this.auth0Client = auth0Client;
	}

	@PreAuthorize("hasAuthority('user') or hasAuthority('admin')")
	@UserDataSave
	public Team createTeam(final Team team) {
		Assert.notNull(team);
		final Team savedTeam = teamRepository.save(team);
		sendTeamCreatedMessage(savedTeam);
		return savedTeam;
	}

	private void sendTeamCreatedMessage(final Team savedTeam) {
		final Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		final UserProfile userProfile = auth0Client.getUser((Auth0JWTToken) auth);
		final TeamCreatedMessage message = new TeamCreatedMessage(savedTeam, userProfile.getId(), new Date());
		rabbitTemplate.convertAndSend(teamExchangeName, teamCreatedRoutingKey, message);
	}

}
