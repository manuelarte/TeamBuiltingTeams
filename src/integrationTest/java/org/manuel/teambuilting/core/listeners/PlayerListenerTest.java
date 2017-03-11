package org.manuel.teambuilting.core.listeners;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.manuel.teambuilting.core.messages.PlayerDeletedMessage;
import org.manuel.teambuilting.core.model.Player;
import org.manuel.teambuilting.core.model.PlayerToTeam;
import org.manuel.teambuilting.core.model.PlayerToTeamSportDetails;
import org.manuel.teambuilting.core.repositories.PlayerToTeamRepository;
import org.manuel.teambuilting.core.repositories.PlayerToTeamSportDetailsRepository;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.test.RabbitListenerTest;
import org.springframework.amqp.rabbit.test.RabbitListenerTestHarness;
import org.springframework.amqp.rabbit.test.RabbitListenerTestHarness.InvocationData;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author manuel.doncel.martos
 * @since 11-3-2017
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@RabbitListenerTest(capture = true)
public class PlayerListenerTest {

	@Value("${messaging.amqp.player.exchange.name}")
	private String playerExchange;

	@Inject
	private PlayerToTeamRepository playerToTeamRepository;

	@Inject
	private PlayerToTeamSportDetailsRepository playerToTeamSportDetailsRepository;

	@Inject
	private RabbitTemplate rabbitTemplate;

	@Inject
	RabbitListenerTestHarness harness;

	@Test
	public void deletePlayerTest() throws InterruptedException {
		final Player player = Player.builder().id("playerId").build();
		savePlayerToTeam(player);
		savePlayerToTeamSportDetails(player);

		final PlayerDeletedMessage event = PlayerDeletedMessage.builder().player(player).date(new Date()).userId("userId").build();

		rabbitTemplate.convertAndSend(playerExchange, "player.deleted", event);

		InvocationData data = harness.getNextInvocationDataFor(PlayerListener.LISTENER_ID, 5, TimeUnit.SECONDS);
		assertNotNull(data);
		assertEquals(1, data.getArguments().length);
		assertEquals(event, ((PlayerDeletedMessage) data.getArguments()[0]));
		assertEquals(0, playerToTeamRepository.findAll().size());
		assertEquals(0, playerToTeamSportDetailsRepository.findAll().size());
	}

	private void savePlayerToTeam(final Player player) {
		final PlayerToTeam playerToTeam = PlayerToTeam.builder().playerId(player.getId()).teamId("teamId").fromDate(new Date()).toDate(new Date()).build();
		playerToTeamRepository.save(playerToTeam);
	}

	private void savePlayerToTeamSportDetails(final Player player) {
		final PlayerToTeamSportDetails playerToTeamSportDetails = PlayerToTeamSportDetails.builder().playerId(player.getId()).sport("Football").mainPosition("GK").build();
		playerToTeamSportDetailsRepository.save(playerToTeamSportDetails);
	}

}