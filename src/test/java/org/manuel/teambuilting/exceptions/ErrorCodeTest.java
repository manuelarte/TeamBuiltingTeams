package org.manuel.teambuilting.exceptions;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.manuel.teambuilting.teams.model.Team;

/**
 * @author manuel.doncel.martos
 * @since 12-3-2017
 */
@RunWith(BlockJUnit4ClassRunner.class)
public class ErrorCodeTest {

	@Test
	public void testGetMessageOfIdNotFound() {
		final String expected = "Entity Team with id teamId not found";
		final String actual = ErrorCode.ID_NOT_FOUND.getMessage(Team.class.getSimpleName(), "teamId");
		assertEquals(actual, expected);
	}

}
