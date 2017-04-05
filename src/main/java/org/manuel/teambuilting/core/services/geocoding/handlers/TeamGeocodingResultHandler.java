package org.manuel.teambuilting.core.services.geocoding.handlers;

import com.google.maps.PendingResult.Callback;
import com.google.maps.model.GeocodingResult;

import org.manuel.teambuilting.core.repositories.TeamGeocodingRepository;
import org.manuel.teambuilting.core.util.Util;
import org.springframework.util.Assert;

import lombok.extern.slf4j.Slf4j;

/**
 * @author manuel.doncel.martos
 * @since 14-3-2017
 */
@Slf4j
public class TeamGeocodingResultHandler implements Callback<GeocodingResult[]> {

	private final String teamId;
	private final TeamGeocodingRepository repository;
	private final Util util;

	public TeamGeocodingResultHandler(final String teamId, final TeamGeocodingRepository repository,
		final Util util) {
		Assert.hasLength(teamId);
		Assert.notNull(repository);
		this.teamId = teamId;
		this.repository = repository;
		this.util = util;
	}

	@Override
	public void onResult(final GeocodingResult[] results) {
		Assert.notNull(results);
		repository.save(util.getTeamGeocodingFrom(teamId, results));
	}

	@Override
	public void onFailure(final Throwable e) {
		log.error("Not able to store the geocoding data", e);
	}
}
