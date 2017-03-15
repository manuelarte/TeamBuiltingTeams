package org.manuel.teambuilting.core.services.query;

import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.PendingResult.Callback;
import com.google.maps.model.GeocodingResult;

import java.util.Optional;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

/**
 * @author manuel.doncel.martos
 * @since 14-3-2017
 */
@Service
@Slf4j
public class GoogleMapsApiService {

	private final GeoApiContext geoApiContext;

	@Inject
	public GoogleMapsApiService(final GeoApiContext geoApiContext) {
		this.geoApiContext = geoApiContext;
	}

	public Optional<GeocodingResult[]> getSynchronously(final String address) {
		return Optional.ofNullable(GeocodingApi.newRequest(geoApiContext).address(address).awaitIgnoreError());
	}

	public void getASynchronously(final String address, final Callback<GeocodingResult[]> callback) {
		GeocodingApi.newRequest(geoApiContext).address(address).setCallback(callback);
	}

	private Callback<GeocodingResult[]> addressRetrievedHandle(final String address) {
		return new Callback<GeocodingResult[]>() {
			@Override
			public void onResult(final GeocodingResult[] results) {
				// getInformationFrom(results);
			}

			@Override
			public void onFailure(final Throwable e) {
				log.error("TeamGeocoding not able to be retrieved for address " + address);
			}
		};
	}
}
