package org.manuel.teambuilting.teams.services.query.impl;

import com.auth0.Auth0User;
import org.manuel.teambuilting.messages.TeamVisitedEvent;
import org.manuel.teambuilting.teams.model.Team;
import org.manuel.teambuilting.teams.repositories.TeamRepository;
import org.manuel.teambuilting.teams.services.query.TeamQueryService;
import org.manuel.teambuilting.teams.util.Util;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.time.Instant;
import java.util.Optional;

/**
 * @author manuel.doncel.martos
 * @since 16-12-2016
 */
@Service
class TeamQueryServiceImpl extends AbstractQueryService<Team, String, TeamRepository> implements TeamQueryService {

	private String teamExchangeName;
	private final RabbitTemplate rabbitTemplate;
	private final Util util;

	@Inject
	public TeamQueryServiceImpl(final @Value("${messaging.amqp.team.exchange.name}") String teamExchangeName,
		final TeamRepository repository, final RabbitTemplate rabbitTemplate, final Util util) {
		super(repository);
		this.teamExchangeName = teamExchangeName;
		this.rabbitTemplate = rabbitTemplate;
		this.util = util;
	}

	@Override
	@Cacheable
	public Page<Team> findTeamBy(final Pageable pageable, final String sport, final String name) {
		return repository.findBySportLikeIgnoreCaseAndNameLikeIgnoreCase(pageable, sport, name);
	}

	@Override
	void postFindOne(final Optional<Team> team) {
		if (team.isPresent()) {
			sendTeamVisitedMessage(team.get());
		}
	}

	private void sendTeamVisitedMessage(final Team visitedTeam) {
		final Optional<Auth0User> userProfile = util.getUserProfile();
		final String userId = userProfile.isPresent() ? userProfile.get().getUserId() : null;
		final TeamVisitedEvent event = new TeamVisitedEvent(visitedTeam.getId(), userId, Instant.now());
		// rabbitTemplate.convertAndSend(teamExchangeName, event.getRoutingKey(), event);
	}

}
