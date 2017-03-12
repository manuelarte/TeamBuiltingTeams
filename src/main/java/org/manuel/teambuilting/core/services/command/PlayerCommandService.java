package org.manuel.teambuilting.core.services.command;

import com.auth0.authentication.result.UserProfile;

import java.util.Date;

import javax.inject.Inject;

import org.manuel.teambuilting.core.aspects.UserDataDeletePlayer;
import org.manuel.teambuilting.core.aspects.UserDataSave;
import org.manuel.teambuilting.core.messages.PlayerDeletedMessage;
import org.manuel.teambuilting.core.model.Player;
import org.manuel.teambuilting.core.repositories.PlayerRepository;
import org.manuel.teambuilting.core.util.Util;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

@Service
public class PlayerCommandService {

	private static final String PLAYER_DELETED_ROUTING_KEY = "player.deleted";

	private final String playerExchangeName;
	private final PlayerRepository playerRepository;
	private final RabbitTemplate rabbitTemplate;
	private final Util util;

	@Inject
	public PlayerCommandService(final @Value("${messaging.amqp.player.exchange.name}") String playerExchangeName,
		final PlayerRepository playerRepository, final RabbitTemplate rabbitTemplate, final Util util) {
		this.playerExchangeName = playerExchangeName;
		this.playerRepository = playerRepository;
		this.rabbitTemplate = rabbitTemplate;
		this.util = util;
	}

	@PreAuthorize("hasAuthority('user') or hasAuthority('admin')")
	@UserDataSave
	public Player savePlayer(final Player player) {
		return playerRepository.save(player);
	}

	@PreAuthorize("hasAuthority('user') or hasAuthority('admin')")
	@UserDataDeletePlayer
	public void deletePlayer(final String playerId) {
		playerRepository.delete(playerId);
		sendPlayerDeletedMessage(playerRepository.findOne(playerId));
	}

	private void sendPlayerDeletedMessage(final Player player) {
		final UserProfile userProfile = util.getUserProfile().get();
		final PlayerDeletedMessage message = new PlayerDeletedMessage(player, userProfile.getId(), new Date());
		rabbitTemplate.convertAndSend(playerExchangeName, PLAYER_DELETED_ROUTING_KEY, message);
	}

}