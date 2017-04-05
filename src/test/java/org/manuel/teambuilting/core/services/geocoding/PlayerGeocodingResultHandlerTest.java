package org.manuel.teambuilting.core.services.geocoding;

import static org.mockito.Mockito.verify;

import com.google.maps.model.GeocodingResult;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.manuel.teambuilting.core.model.PlayerGeocoding;
import org.manuel.teambuilting.core.repositories.PlayerGeocodingRepository;
import org.manuel.teambuilting.core.services.geocoding.handlers.PlayerGeocodingResultHandler;
import org.manuel.teambuilting.core.util.Util;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * @author manuel.doncel.martos
 * @since 15-3-2017
 */
@RunWith(MockitoJUnitRunner.class)
public class PlayerGeocodingResultHandlerTest {

	@Mock
	private PlayerGeocodingRepository playerGeocodingRepository;

	@Test
	public void savePlayerGeocoding() {
		final Util util = new Util(null);
		final PlayerGeocodingResultHandler handler = new PlayerGeocodingResultHandler("playerId", playerGeocodingRepository, util);
		final GeocodingResult[] results = GeocodingExamples.ubeda();
		handler.onResult(results);
		final PlayerGeocoding expected = util.getPlayerGeocodingFrom("playerId", results);
		verify(playerGeocodingRepository).save(expected);
	}
}
