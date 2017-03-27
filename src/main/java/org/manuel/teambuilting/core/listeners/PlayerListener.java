package org.manuel.teambuilting.core.listeners;

import static org.manuel.teambuilting.core.listeners.PlayerListener.LISTENER_ID;

import javax.inject.Inject;

import org.manuel.teambuilting.messages.PlayerDeletedMessage;
import org.manuel.teambuilting.core.repositories.PlayerToTeamRepository;
import org.manuel.teambuilting.core.repositories.PlayerToTeamSportDetailsRepository;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

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
public class PlayerListener {

    public static final String LISTENER_ID = "PlayerListenerId";

    private final PlayerToTeamRepository playerToTeamRepository;
    private final PlayerToTeamSportDetailsRepository playerToTeamSportDetailsRepository;

    @Inject
    public PlayerListener(final PlayerToTeamRepository playerToTeamRepository, final PlayerToTeamSportDetailsRepository playerToTeamSportDetailsRepository) {
        this.playerToTeamRepository = playerToTeamRepository;
        this.playerToTeamSportDetailsRepository = playerToTeamSportDetailsRepository;
    }

    @RabbitHandler
    public void handle(final PlayerDeletedMessage message) {
        playerToTeamRepository.delete(playerToTeamRepository.findByPlayerId(message.getPlayer().getId()));
        playerToTeamSportDetailsRepository.delete(playerToTeamSportDetailsRepository.findByPlayerId(message.getPlayer().getId()));
    }

}
