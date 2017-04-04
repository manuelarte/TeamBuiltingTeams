package org.manuel.teambuilting.core.services.command.impl;

import com.auth0.authentication.result.UserProfile;

import java.util.Date;

import javax.inject.Inject;

import org.manuel.teambuilting.core.aspects.UserDataDeletePlayer;
import org.manuel.teambuilting.core.aspects.UserDataSave;
import org.manuel.teambuilting.core.model.Player;
import org.manuel.teambuilting.core.repositories.PlayerRepository;
import org.manuel.teambuilting.core.services.command.PlayerCommandService;
import org.manuel.teambuilting.core.util.Util;
import org.manuel.teambuilting.messages.PlayerDeletedEvent;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
class PlayerCommandServiceImpl extends AbstractCommandService<Player, String, PlayerRepository> implements PlayerCommandService {

	private final String playerExchangeName;
	private final RabbitTemplate rabbitTemplate;
	private final Util util;

	@Inject
	public PlayerCommandServiceImpl(final @Value("${messaging.amqp.player.exchange.name}") String playerExchangeName,
		final PlayerRepository repository, final RabbitTemplate rabbitTemplate, final Util util) {
		super(repository);
		this.playerExchangeName = playerExchangeName;
		this.rabbitTemplate = rabbitTemplate;
		this.util = util;
	}

	@Override
	@UserDataSave
	public Player save(final Player player) {
		// need to call like this because the annotation @UserDataSave
		return super.save(player);
	}

	@Override
	@UserDataDeletePlayer
	void afterDeleted(final String playerId) {
		sendPlayerDeletedEvent(playerId);
	}

	private void sendPlayerDeletedEvent(final String playerId) {
		final UserProfile userProfile = util.getUserProfile().get();
		final PlayerDeletedEvent event = new PlayerDeletedEvent(playerId, userProfile.getId(), new Date());
		rabbitTemplate.convertAndSend(playerExchangeName, event.getRoutingKey(), event);
	}

}