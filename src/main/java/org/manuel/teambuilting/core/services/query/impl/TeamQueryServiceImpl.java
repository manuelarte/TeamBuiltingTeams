package org.manuel.teambuilting.core.services.query.impl;

import com.auth0.authentication.result.UserProfile;

import java.util.Date;
import java.util.Optional;

import javax.inject.Inject;

import org.manuel.teambuilting.messages.TeamVisitedEvent;
import org.manuel.teambuilting.core.model.Team;
import org.manuel.teambuilting.core.repositories.TeamRepository;
import org.manuel.teambuilting.core.services.query.TeamQueryService;
import org.manuel.teambuilting.core.util.Util;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * @author manuel.doncel.martos
 * @since 16-12-2016
 */
@Service
class TeamQueryServiceImpl extends AbstractQueryService<Team, String, TeamRepository> implements TeamQueryService {

	private static final String TEAM_VISITED_ROUTING_KEY = "team.visited";

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
		final Optional<UserProfile> userProfile = util.getUserProfile();
		final String userId = userProfile.isPresent() ? userProfile.get().getId() : null;
		final TeamVisitedEvent event = new TeamVisitedEvent(visitedTeam.getId(), userId, new Date());
		// rabbitTemplate.convertAndSend(teamExchangeName, event.getRoutingKey(), event);
	}

}
