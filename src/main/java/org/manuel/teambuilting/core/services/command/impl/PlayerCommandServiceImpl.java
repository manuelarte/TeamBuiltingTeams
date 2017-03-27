package org.manuel.teambuilting.core.services.command.impl;

import com.auth0.authentication.result.UserProfile;
import org.manuel.teambuilting.core.aspects.UserDataDeletePlayer;
import org.manuel.teambuilting.core.aspects.UserDataSave;
import org.manuel.teambuilting.messages.PlayerDeletedMessage;
import org.manuel.teambuilting.core.model.Player;
import org.manuel.teambuilting.core.repositories.PlayerRepository;
import org.manuel.teambuilting.core.services.command.PlayerCommandService;
import org.manuel.teambuilting.core.util.Util;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.Date;

@Service
class PlayerCommandServiceImpl implements PlayerCommandService {

	private final String playerExchangeName;
	private final PlayerRepository playerRepository;
	private final RabbitTemplate rabbitTemplate;
	private final Util util;

	@Inject
	public PlayerCommandServiceImpl(final @Value("${messaging.amqp.player.exchange.name}") String playerExchangeName,
		final PlayerRepository playerRepository, final RabbitTemplate rabbitTemplate, final Util util) {
		this.playerExchangeName = playerExchangeName;
		this.playerRepository = playerRepository;
		this.rabbitTemplate = rabbitTemplate;
		this.util = util;
	}

	@Override
	@PreAuthorize("hasAuthority('user') or hasAuthority('admin')")
	@UserDataSave
	public Player save(final Player player) {
		return playerRepository.save(player);
	}

	@Override
	@PreAuthorize("hasAuthority('user') or hasAuthority('admin')")
	@UserDataDeletePlayer
	public void delete(final String playerId) {
		final Player playerToBeDeleted = playerRepository.findOne(playerId);
		playerRepository.delete(playerId);
		sendPlayerDeletedMessage(playerToBeDeleted);
	}

	private void sendPlayerDeletedMessage(final Player player) {
		final UserProfile userProfile = util.getUserProfile().get();
		final PlayerDeletedMessage message = new PlayerDeletedMessage(player, userProfile.getId(), new Date());
		rabbitTemplate.convertAndSend(playerExchangeName, PlayerDeletedMessage.ROUTING_KEY, message);
	}

}