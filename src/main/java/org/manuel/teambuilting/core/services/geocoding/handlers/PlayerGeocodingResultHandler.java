package org.manuel.teambuilting.core.services.geocoding.handlers;

import com.google.maps.PendingResult.Callback;
import com.google.maps.model.GeocodingResult;

import org.manuel.teambuilting.core.repositories.PlayerGeocodingRepository;
import org.manuel.teambuilting.core.util.Util;
import org.springframework.util.Assert;

import lombok.extern.slf4j.Slf4j;

/**
 * @author manuel.doncel.martos
 * @since 14-3-2017
 */
@Slf4j
public class PlayerGeocodingResultHandler implements Callback<GeocodingResult[]> {

	private final String playerId;
	private final PlayerGeocodingRepository repository;
	private final Util util;

	public PlayerGeocodingResultHandler(final String playerId, final PlayerGeocodingRepository repository, final Util util) {
		Assert.hasLength(playerId);
		Assert.notNull(repository);
		this.playerId = playerId;
		this.repository = repository;
		this.util = util;
	}

	@Override
	public void onResult(final GeocodingResult[] results) {
		Assert.notNull(results);
		repository.save(util.getPlayerGeocodingFrom(playerId, results));
	}

	@Override
	public void onFailure(final Throwable e) {
		log.error("Not able to store the geocoding data for", e);
	}
}
