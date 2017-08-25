package org.manuel.teambuilting.teams.services.geocoding;

import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.GeocodingApiRequest;

import lombok.AllArgsConstructor;
import org.manuel.teambuilting.teams.model.Team;
import org.manuel.teambuilting.teams.repositories.TeamGeocodingRepository;
import org.manuel.teambuilting.teams.services.geocoding.handlers.TeamGeocodingResultHandler;
import org.manuel.teambuilting.teams.util.TeamUtil;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

/**
 * @author Manuel Doncel Martos
 * @since 26/03/2017.
 */
@Service
@AllArgsConstructor
public class TeamGeocodingService {

    private final GeoApiContext geoApiContext;
    private final TeamGeocodingRepository teamGeocodingRepository;
    private final TeamUtil util;

    public void asyncReq(final Team team) {
        Assert.notNull(team);
        Assert.hasLength(team.getLocation());
        final GeocodingApiRequest req = GeocodingApi.newRequest(geoApiContext).address(team.getLocation());
        req.setCallback(new TeamGeocodingResultHandler(team.getId(), teamGeocodingRepository, util));
    }
}
