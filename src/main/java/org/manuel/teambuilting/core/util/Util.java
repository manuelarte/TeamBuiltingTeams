package org.manuel.teambuilting.core.util;

import com.auth0.authentication.result.UserProfile;
import com.auth0.spring.security.api.Auth0JWTToken;
import com.google.maps.model.AddressComponent;
import com.google.maps.model.GeocodingResult;
import com.google.maps.model.LatLng;
import org.manuel.teambuilting.core.config.Auth0Client;
import org.manuel.teambuilting.core.model.PlayerGeocoding;
import org.manuel.teambuilting.core.model.TeamGeocoding;
import org.manuel.teambuilting.core.model.TimeSlice;
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

	public Optional<UserProfile> getUserProfile() {
		final Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		return auth instanceof Auth0JWTToken ? Optional.of(auth0Client.getUser((Auth0JWTToken) auth)) : Optional.empty();
	}

	public PlayerGeocoding getPlayerGeocodingFrom(final String playerId, final GeocodingResult[] results) {
		Assert.notNull(results);
		Assert.isTrue(results.length > 0);
		final Map<String, String> map = new HashMap<>(results[0].addressComponents.length);
		for (final AddressComponent addressComponent : results[0].addressComponents) {
			map.put(addressComponent.types[0].toCanonicalLiteral(), addressComponent.longName);
		}
		final LatLng location = results[0].geometry.location;
		final String placeId = results[0].placeId;
		return PlayerGeocoding.builder().addressComponents(map).entityId(playerId)
			.lat(location.lat).lng(location.lng).build();
	}

	public TeamGeocoding getTeamGeocodingFrom(final String teamId, final GeocodingResult[] results) {
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

	/**
	 * Returns if there is an overlap between two dates. FromDate is inclusive, ToDate is exclusive
	 * @param entryOne
	 * @param entryTwo
	 * @param <T>
	 * @return
	 */
	public <T extends TimeSlice> boolean isOverlapping(final T entryOne, final T entryTwo) {
		Assert.notNull(entryOne);
		Assert.notNull(entryTwo);
		boolean toReturn;
		if (entryOne.getFromDate().equals(entryTwo.getFromDate())) {
			toReturn = true;
		}
		else if (entryOne.getFromDate().after(entryTwo.getFromDate())) {
			toReturn = isOverlapping(entryTwo, entryOne);
		} else {
			final boolean entryTwoFromDateBetweenEntryOneDates = entryTwo.getToDate() == null || entryTwo.getFromDate().after(entryOne.getFromDate());
			toReturn = entryTwoFromDateBetweenEntryOneDates;
		}
		return toReturn;
	}
}
