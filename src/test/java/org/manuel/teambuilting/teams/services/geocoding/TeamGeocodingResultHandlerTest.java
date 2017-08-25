package org.manuel.teambuilting.teams.services.geocoding;

import com.google.maps.model.GeocodingResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.manuel.teambuilting.teams.model.TeamGeocoding;
import org.manuel.teambuilting.teams.repositories.TeamGeocodingRepository;
import org.manuel.teambuilting.teams.services.geocoding.handlers.TeamGeocodingResultHandler;
import org.manuel.teambuilting.teams.util.TeamUtil;
import org.mockito.Mock;

import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

/**
 * @author manuel.doncel.martos
 * @since 15-3-2017
 */
public class TeamGeocodingResultHandlerTest {

	@Mock
	private TeamGeocodingRepository teamGeocodingRepository;

	@BeforeEach
    public void beforeEach() {
	    initMocks(this);
    }

	@Test
	public void savePlayerGeocoding() {
		final TeamUtil util = new TeamUtil();
		final TeamGeocodingResultHandler handler = new TeamGeocodingResultHandler("playerId", teamGeocodingRepository, util);
		final GeocodingResult[] results = GeocodingExamples.ubeda();
		handler.onResult(results);
		final TeamGeocoding expected = util.getTeamGeocodingFrom("playerId", results);
		verify(teamGeocodingRepository).save(expected);
	}
}
