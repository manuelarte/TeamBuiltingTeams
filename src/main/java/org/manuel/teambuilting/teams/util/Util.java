package org.manuel.teambuilting.teams.util;

import com.auth0.authentication.result.UserProfile;
import com.auth0.spring.security.api.Auth0JWTToken;
import com.google.maps.model.AddressComponent;
import com.google.maps.model.GeocodingResult;
import com.google.maps.model.LatLng;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.inject.Inject;

import org.manuel.teambuilting.teams.config.Auth0Client;
import org.manuel.teambuilting.teams.model.TeamGeocoding;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

/**
 * @author manuel.doncel.martos
 * @since 11-3-2017
 */
@Component
public class Util {

	private final Auth0Client auth0Client;

	@Inject
	public Util(final Auth0Client auth0Client) {
		this.auth0Client = auth0Client;
	}

	public Optional<UserProfile> getUserProfile() {
		final Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		return auth instanceof Auth0JWTToken ? Optional.of(auth0Client.getUser((Auth0JWTToken) auth)) : Optional.empty();
	}

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
