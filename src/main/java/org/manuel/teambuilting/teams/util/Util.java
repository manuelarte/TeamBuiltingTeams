package org.manuel.teambuilting.teams.util;

import com.auth0.Auth0Client;
import com.auth0.Auth0User;
import com.auth0.Tokens;
import com.auth0.spring.security.api.authentication.AuthenticationJsonWebToken;
import com.google.maps.model.AddressComponent;
import com.google.maps.model.GeocodingResult;
import com.google.maps.model.LatLng;
import org.manuel.teambuilting.teams.model.TeamGeocoding;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

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

	public Optional<Auth0User> getUserProfile() {
		Optional<Auth0User> toReturn = Optional.empty();
		final Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth instanceof AuthenticationJsonWebToken) {
			final String token = ((AuthenticationJsonWebToken) auth).getToken();
			toReturn = Optional.of(auth0Client.getUserProfile(new Tokens(token, null, "JWT", null)));
		}
		return toReturn;
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
