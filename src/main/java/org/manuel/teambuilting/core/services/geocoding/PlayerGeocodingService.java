package org.manuel.teambuilting.core.services.geocoding;

import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.GeocodingApiRequest;

import org.manuel.teambuilting.core.model.Player;
import org.manuel.teambuilting.core.repositories.PlayerGeocodingRepository;
import org.manuel.teambuilting.core.services.geocoding.handlers.PlayerGeocodingResultHandler;
import org.manuel.teambuilting.core.util.Util;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

/**
 * @author Manuel Doncel Martos
 * @since 26/03/2017.
 */
@Service
public class PlayerGeocodingService {

    private final GeoApiContext geoApiContext;
    private final PlayerGeocodingRepository playerGeocodingRepository;
    private final Util util;

    public PlayerGeocodingService(final GeoApiContext geoApiContext,
        final PlayerGeocodingRepository playerGeocodingRepository, final Util util) {
        this.geoApiContext = geoApiContext;
        this.playerGeocodingRepository = playerGeocodingRepository;
        this.util = util;
    }

    public void asyncReq(final Player player) {
        Assert.notNull(player);
        Assert.hasLength(player.getBornAddress());
        final GeocodingApiRequest req = GeocodingApi.newRequest(geoApiContext).address(player.getBornAddress());
        req.setCallback(new PlayerGeocodingResultHandler(player.getId(), playerGeocodingRepository, util));

    }
}
