package org.manuel.teambuilting.core.services.geocoding;

import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.GeocodingApiRequest;

import org.manuel.teambuilting.core.model.Team;
import org.manuel.teambuilting.core.repositories.TeamGeocodingRepository;
import org.manuel.teambuilting.core.services.geocoding.handlers.TeamGeocodingResultHandler;
import org.manuel.teambuilting.core.util.Util;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

/**
 * @author Manuel Doncel Martos
 * @since 26/03/2017.
 */
@Service
public class TeamGeocodingService {

    private final GeoApiContext geoApiContext;
    private final TeamGeocodingRepository teamGeocodingRepository;
    private final Util util;

    public TeamGeocodingService(final GeoApiContext geoApiContext, final TeamGeocodingRepository teamGeocodingRepository,
        final Util util) {
        this.geoApiContext = geoApiContext;
        this.teamGeocodingRepository = teamGeocodingRepository;
        this.util = util;
    }

    public void asyncReq(final Team team) {
        Assert.notNull(team);
        Assert.hasLength(team.getLocation());
        final GeocodingApiRequest req = GeocodingApi.newRequest(geoApiContext).address(team.getLocation());
        req.setCallback(new TeamGeocodingResultHandler(team.getId(), teamGeocodingRepository, util));
    }
}
