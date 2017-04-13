package org.manuel.teambuilting.teams.services.geocoding;

import static org.mockito.Mockito.verify;

import com.google.maps.model.GeocodingResult;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.manuel.teambuilting.teams.model.TeamGeocoding;
import org.manuel.teambuilting.teams.services.geocoding.handlers.TeamGeocodingResultHandler;
import org.manuel.teambuilting.teams.repositories.TeamGeocodingRepository;
import org.manuel.teambuilting.teams.util.Util;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * @author manuel.doncel.martos
 * @since 15-3-2017
 */
@RunWith(MockitoJUnitRunner.class)
public class TeamGeocodingResultHandlerTest {

	@Mock
	private TeamGeocodingRepository teamGeocodingRepository;

	@Test
	public void savePlayerGeocoding() {
		final Util util = new Util(null);
		final TeamGeocodingResultHandler handler = new TeamGeocodingResultHandler("playerId", teamGeocodingRepository, util);
		final GeocodingResult[] results = GeocodingExamples.ubeda();
		handler.onResult(results);
		final TeamGeocoding expected = util.getTeamGeocodingFrom("playerId", results);
		verify(teamGeocodingRepository).save(expected);
	}
}
