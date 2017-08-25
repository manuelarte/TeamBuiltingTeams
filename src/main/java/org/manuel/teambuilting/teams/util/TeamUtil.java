package org.manuel.teambuilting.teams.util;

import com.google.maps.model.AddressComponent;
import com.google.maps.model.GeocodingResult;
import com.google.maps.model.LatLng;
import lombok.AllArgsConstructor;
import org.manuel.teambuilting.teams.model.TeamGeocoding;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.HashMap;
import java.util.Map;

/**
 * @author manuel.doncel.martos
 * @since 11-3-2017
 */
@Component
@AllArgsConstructor
public class TeamUtil {

	public TeamGeocoding getTeamGeocodingFrom(final String teamId, final GeocodingResult[] results) {
		Assert.notNull(results);
		Assert.isTrue(results.length > 0);
		final Map<String, String> map = new HashMap<>(results[0].addressComponents.length);
		for (final AddressComponent addressComponent : results[0].addressComponents) {
			map.put(addressComponent.types[0].toCanonicalLiteral(), addressComponent.longName);
		}
		final LatLng location = results[0].geometry.location;
		return TeamGeocoding.builder().addressComponents(map).teamId(teamId)
			.lat(location.lat).lng(location.lng).build();
	}

}
