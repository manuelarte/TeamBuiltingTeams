package org.manuel.teambuilting.core.services.query.impl;

import com.auth0.authentication.result.UserProfile;

import java.util.Date;
import java.util.Optional;

import javax.inject.Inject;

import org.manuel.teambuilting.messages.PlayerVisitedEvent;
import org.manuel.teambuilting.core.model.Player;
import org.manuel.teambuilting.core.repositories.PlayerRepository;
import org.manuel.teambuilting.core.services.query.PlayerQueryService;
import org.manuel.teambuilting.core.util.Util;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

/**
 * @author manuel.doncel.martos
 * @since 16-12-2016
 */
@Service
class PlayerQueryServiceImpl extends AbstractQueryService<Player, String, PlayerRepository> implements PlayerQueryService {

	private static final String PLAYER_VISITED_ROUTING_KEY = "player.visited";


	/**
	 * Exchange name for the players
	 */
	private final String playerExchangeName;
	private final RabbitTemplate rabbitTemplate;
	private final Util util;

	@Inject
	public PlayerQueryServiceImpl(final @Value("${messaging.amqp.player.exchange.name}") String playerExchangeName,
		final PlayerRepository playerRepository, final RabbitTemplate rabbitTemplate, final Util util) {
		super(playerRepository);
		this.playerExchangeName = playerExchangeName;
		this.rabbitTemplate = rabbitTemplate;
		this.util = util;
	}

	@Override
	void postFindOne(final Optional<Player> player) {
		Assert.notNull(player);
		if (player.isPresent()) {
			sendPlayerVisitedMessage(player.get());
		}
	}

	@Override
	public Page<Player> findPlayerByName(final Pageable pageable, final String name) {
		return repository.findByNameLikeIgnoreCase(pageable, name);
	}

	private void sendPlayerVisitedMessage(final Player visitedPlayer) {
		final Optional<UserProfile> userProfile = util.getUserProfile();
		final String userId = userProfile.isPresent() ? userProfile.get().getId() : null;
		final PlayerVisitedEvent message = new PlayerVisitedEvent(visitedPlayer.getId(), userId, new Date());
		// rabbitTemplate.convertAndSend(playerExchangeName, PLAYER_VISITED_ROUTING_KEY, message);
	}

}
