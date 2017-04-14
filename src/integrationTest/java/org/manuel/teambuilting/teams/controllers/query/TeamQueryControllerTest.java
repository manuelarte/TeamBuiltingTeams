package org.manuel.teambuilting.teams.controllers.query;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.manuel.teambuilting.exceptions.ErrorCode;
import org.manuel.teambuilting.exceptions.ExceptionMessage;
import org.manuel.teambuilting.teams.model.Team;
import org.manuel.teambuilting.teams.repositories.TeamRepository;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.inject.Inject;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author manuel.doncel.martos
 * @since 5-4-2017
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class TeamQueryControllerTest {

	@Inject
	private WebApplicationContext context;

	@Inject
	private TeamRepository teamRepository;

	private MockMvc mvc;

	private final ObjectMapper mapper = new ObjectMapper();

	@Before
	public void setup() {
		mvc = MockMvcBuilders.webAppContextSetup(context).build();
	}

	@Test
	public void findOneTeamThatExists() throws Exception {
		final Team expected = Team.builder().name("name").build();
		teamRepository.save(expected);

		final String contentAsString = mvc.perform(get("/teams/" + expected.getId(), "")).andExpect(status().isOk()).andReturn().getResponse()
			.getContentAsString();
		final Team actual = mapper.readValue(contentAsString, Team.class);
		assertEquals(expected, actual);
	}

	@Test
	public void findOneTeamThatDoesNotExist() throws Exception {
		final String id = UUID.randomUUID().toString();
		final String contentAsString = mvc.perform(get("/teams/" + id, "")).andExpect(status().is4xxClientError()).andReturn().getResponse()
			.getContentAsString();

		final ExceptionMessage actual = mapper.readValue(contentAsString, ExceptionMessage.class);
		assertEquals(ErrorCode.ID_NOT_FOUND.getErrorCode(), actual.getErrorCode());
	}
}
