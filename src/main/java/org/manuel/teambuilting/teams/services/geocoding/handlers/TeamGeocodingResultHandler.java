package org.manuel.teambuilting.teams.services.geocoding.handlers;

import com.google.maps.PendingResult.Callback;
import com.google.maps.model.GeocodingResult;

import org.manuel.teambuilting.teams.repositories.TeamGeocodingRepository;
import org.manuel.teambuilting.teams.util.TeamUtil;
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
	private final TeamUtil util;

	public TeamGeocodingResultHandler(final String teamId, final TeamGeocodingRepository repository,
		final TeamUtil util) {
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
