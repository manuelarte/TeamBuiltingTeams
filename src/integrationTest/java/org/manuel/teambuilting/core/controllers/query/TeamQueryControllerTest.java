package org.manuel.teambuilting.core.controllers.query;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.UUID;

import javax.inject.Inject;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.manuel.teambuilting.core.exceptions.ErrorCode;
import org.manuel.teambuilting.core.exceptions.ExceptionMessage;
import org.manuel.teambuilting.core.model.Team;
import org.manuel.teambuilting.core.repositories.TeamRepository;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

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

		final String contentAsString = mvc.perform(get("/core/teams/" + expected.getId(), "")).andExpect(status().isOk()).andReturn().getResponse()
			.getContentAsString();
		final Team actual = mapper.readValue(contentAsString, Team.class);
		assertEquals(expected, actual);
	}

	@Test
	public void findOneTeamThatDoesNotExist() throws Exception {
		final String id = UUID.randomUUID().toString();
		final String contentAsString = mvc.perform(get("/core/teams/" + id, "")).andExpect(status().is4xxClientError()).andReturn().getResponse()
			.getContentAsString();

		final ExceptionMessage actual = mapper.readValue(contentAsString, ExceptionMessage.class);
		assertEquals(ErrorCode.ID_NOT_FOUND.getErrorCode(), actual.getErrorCode());
	}
}
