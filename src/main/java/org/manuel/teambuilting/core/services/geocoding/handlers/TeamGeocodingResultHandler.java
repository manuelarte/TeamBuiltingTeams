package org.manuel.teambuilting.core.services.geocoding.handlers;

import com.google.maps.PendingResult.Callback;
import com.google.maps.model.AddressComponent;
import com.google.maps.model.GeocodingResult;
import com.google.maps.model.LatLng;
import lombok.extern.slf4j.Slf4j;
import org.manuel.teambuilting.core.model.TeamGeocoding;
import org.manuel.teambuilting.core.repositories.TeamGeocodingRepository;
import org.springframework.util.Assert;

import java.util.HashMap;
import java.util.Map;

/**
 * @author manuel.doncel.martos
 * @since 14-3-2017
 */
@Slf4j
public class TeamGeocodingResultHandler implements Callback<GeocodingResult[]> {

	private final String teamId;
	private final TeamGeocodingRepository repository;

	public TeamGeocodingResultHandler(final String teamId, final TeamGeocodingRepository repository) {
		Assert.hasLength(teamId);
		Assert.notNull(repository);
		this.teamId = teamId;
		this.repository = repository;
	}

	@Override
	public void onResult(final GeocodingResult[] results) {
		Assert.notNull(results);
		repository.save(getGeocodingFrom(results));
	}

	@Override
	public void onFailure(final Throwable e) {
		log.error("Not able to store the geocoding data", e);
	}

	private TeamGeocoding getGeocodingFrom(final GeocodingResult[] results) {
		Assert.notNull(results);
		Assert.isTrue(results.length > 0);
		final Map<String, String> map = new HashMap<>(results[0].addressComponents.length);
		for (final AddressComponent addressComponent : results[0].addressComponents) {
			map.put(addressComponent.types[0].toCanonicalLiteral(), addressComponent.longName);
		}
		final LatLng location = results[0].geometry.location;
		final String placeId = results[0].placeId;
		return TeamGeocoding.builder().addressComponents(map).entityId(teamId)
			.lat(location.lat).lng(location.lng).build();
	}
}