/**
 * 
 */
package org.manuel.teambuilting.core.services.command.impl;

import com.auth0.authentication.result.UserProfile;

import java.util.Date;

import javax.inject.Inject;

import org.manuel.teambuilting.core.aspects.UserDataSave;
import org.manuel.teambuilting.messages.TeamCreatedMessage;
import org.manuel.teambuilting.core.model.Team;
import org.manuel.teambuilting.core.repositories.TeamRepository;
import org.manuel.teambuilting.core.services.command.TeamCommandService;
import org.manuel.teambuilting.core.util.Util;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

/**
 * @author Manuel Doncel Martos
 *
 */
@Service
class TeamCommandServiceImpl implements TeamCommandService {

	private static final String TEAM_CREATED_ROUTING_KEY = "team.created";

	private final String teamExchangeName;
	private final TeamRepository teamRepository;
	private final RabbitTemplate rabbitTemplate;
	private final Util util;

	@Inject
	public TeamCommandServiceImpl(final @Value("${messaging.amqp.team.exchange.name}") String teamExchangeName,
		final TeamRepository teamRepository, final RabbitTemplate rabbitTemplate,
		final Util util) {
		this.teamExchangeName = teamExchangeName;
		this.teamRepository = teamRepository;
		this.rabbitTemplate = rabbitTemplate;
		this.util = util;
	}

	@Override
	public void delete(final String s) {
		throw new RuntimeException("Not supported yet");
	}

	@Override
	@PreAuthorize("hasAuthority('user') or hasAuthority('admin')")
	@UserDataSave
	public Team save(final Team team) {
		Assert.notNull(team);
		final Team savedTeam = teamRepository.save(team);
		sendTeamCreatedMessage(savedTeam);
		return savedTeam;
	}

	private void sendTeamCreatedMessage(final Team savedTeam) {
		final UserProfile userProfile = util.getUserProfile().get();
		final TeamCreatedMessage message = new TeamCreatedMessage(savedTeam, userProfile.getId(), new Date());
		rabbitTemplate.convertAndSend(teamExchangeName, TEAM_CREATED_ROUTING_KEY, message);
	}

}