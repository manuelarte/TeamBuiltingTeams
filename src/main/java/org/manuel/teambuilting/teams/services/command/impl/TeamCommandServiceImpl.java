/**
 * 
 */
package org.manuel.teambuilting.teams.services.command.impl;

import com.auth0.Auth0User;
import org.manuel.teambuilting.messages.TeamRegisteredEvent;
import org.manuel.teambuilting.teams.aspects.UserDataSave;
import org.manuel.teambuilting.teams.model.Team;
import org.manuel.teambuilting.teams.repositories.TeamRepository;
import org.manuel.teambuilting.teams.services.command.TeamCommandService;
import org.manuel.teambuilting.teams.util.Util;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.Date;

/**
 * @author Manuel Doncel Martos
 *
 */
@Service
class TeamCommandServiceImpl extends AbstractCommandService<Team, String, TeamRepository> implements TeamCommandService {

	private final String teamExchangeName;
	private final RabbitTemplate rabbitTemplate;
	private final Util util;

	@Inject
	public TeamCommandServiceImpl(final @Value("${messaging.amqp.team.exchange.name}") String teamExchangeName,
		final TeamRepository repository, final RabbitTemplate rabbitTemplate,
		final Util util) {
		super(repository);
		this.teamExchangeName = teamExchangeName;
		this.rabbitTemplate = rabbitTemplate;
		this.util = util;
	}

	@Override
	public void delete(final String s) {
		throw new RuntimeException("Not supported yet");
	}

	@Override
	@UserDataSave
	public Team save(final Team team) {
		return super.save(team);
	}

	@Override
	void afterSaved(final Team savedTeam) {
		sendTeamRegisteredEvent(savedTeam);
	}

	private void sendTeamRegisteredEvent(final Team savedTeam) {
		final Auth0User userProfile = util.getUserProfile().get();
		final TeamRegisteredEvent event = new TeamRegisteredEvent(savedTeam.getId(), userProfile.getUserId(), new Date());
		rabbitTemplate.convertAndSend(teamExchangeName, event.getRoutingKey(), event);
	}

}