package org.manuel.teambuilting.core.listeners;

import lombok.AllArgsConstructor;
import org.manuel.teambuilting.core.repositories.PlayerGeocodingRepository;
import org.manuel.teambuilting.core.repositories.PlayerToTeamRepository;
import org.manuel.teambuilting.core.repositories.PlayerToTeamSportDetailsRepository;
import org.manuel.teambuilting.messages.PlayerDeletedMessage;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.stereotype.Component;

import javax.inject.Inject;

import static org.manuel.teambuilting.core.listeners.PlayerListener.LISTENER_ID;

/**
 * Listener for the player topic
 *
 * @author Manuel Doncel Martos
 * @since 31/12/2016.
 */
@RabbitListener(id = LISTENER_ID, bindings = @QueueBinding(
        value = @Queue(durable = "true", value = "${messaging.amqp.player.queue.name}"),
        exchange = @Exchange(durable = "${messaging.amqp.player.exchange.durable}", value = "${messaging.amqp.player.exchange.name}", type = ExchangeTypes.TOPIC),
        key = "player.deleted"))
@Component
@AllArgsConstructor
public class PlayerListener {

    public static final String LISTENER_ID = "PlayerListenerId";

    private final PlayerToTeamRepository playerToTeamRepository;
    private final PlayerToTeamSportDetailsRepository playerToTeamSportDetailsRepository;
    private final PlayerGeocodingRepository playerGeocodingRepository;

    @RabbitHandler
    public void handle(final PlayerDeletedMessage message) {
        playerToTeamRepository.delete(playerToTeamRepository.findByPlayerId(message.getPlayer().getId()));
        playerToTeamSportDetailsRepository.delete(playerToTeamSportDetailsRepository.findByPlayerId(message.getPlayer().getId()));
        playerGeocodingRepository.delete(playerGeocodingRepository.findByEntityId(message.getPlayer().getId()));
    }

}
