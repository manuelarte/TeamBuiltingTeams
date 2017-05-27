package org.manuel.teambuilting.teams.services.command.impl;

import com.auth0.Auth0User;
import com.auth0.authentication.result.UserProfile;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.manuel.teambuilting.messages.TeamRegisteredEvent;
import org.manuel.teambuilting.teams.model.Team;
import org.manuel.teambuilting.teams.repositories.TeamRepository;
import org.manuel.teambuilting.teams.util.Util;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Optional;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

/**
 * @author manuel.doncel.martos
 * @since 5-4-2017
 */
@RunWith(MockitoJUnitRunner.class)
public class TeamCommandServiceImplTest {

	@Mock
	private TeamRepository teamRepository;
	@Mock
	private RabbitTemplate rabbitTemplate;
	@Mock
	private Util util;

	@InjectMocks
	private TeamCommandServiceImpl teamCommandService;

	@Before
	public void setup() {
	}

	@Test
	public void testSaveProperTeam() {
		final Optional<Auth0User> userProfile = Optional.of(createUserProfile());
		when(util.getUserProfile()).thenReturn(userProfile);
		final Team team = Team.builder().name("team").fromDate(new Date()).toDate(new Date()).build();
		when(teamRepository.save(team)).thenReturn(team);
		teamCommandService.save(team);
		verify(teamRepository, times(1)).save(team);
		verify(rabbitTemplate, times(1)).convertAndSend(any(String.class), eq(TeamRegisteredEvent.ROUTING_KEY), any(TeamRegisteredEvent.class));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testSaveNullTeam() {
		final Optional<Auth0User> userProfile = Optional.of(createUserProfile());
		when(util.getUserProfile()).thenReturn(userProfile);
		teamCommandService.save(null);
	}

	private Auth0User createUserProfile() {
		return new Auth0User(new UserProfile("id", "name", "nickname", "pictureURL", "email", true, "familyName", new Date(), new ArrayList<>(), new HashMap<>(), new HashMap<>(), new HashMap<>(), "givenName"));
	}
}
