package org.manuel.teambuilting.exceptions;

import org.junit.jupiter.api.Test;
import org.manuel.teambuilting.teams.model.Team;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author manuel.doncel.martos
 * @since 12-3-2017
 */
public class ErrorCodeTest {

	@Test
	public void testGetMessageOfIdNotFound() {
		final String expected = "Entity Team with id teamId not found";
		final String actual = ErrorCode.ID_NOT_FOUND.getMessage(Team.class.getSimpleName(), "teamId");
		assertEquals(actual, expected);
	}

}
