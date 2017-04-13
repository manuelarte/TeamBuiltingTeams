package org.manuel.teambuilting.teams.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.mongodb.annotations.Immutable;

import java.util.Map;

import javax.validation.constraints.NotNull;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Manuel Doncel Martos
 *
 */
@Immutable
@Document
@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonDeserialize
public class TeamGeocoding {

	@Id
	protected ObjectId id;

	@NotNull
	protected String teamId;

	@NotNull
	protected double lat;

	@NotNull
	protected double lng;

	@NotNull
	protected Map<String, String> addressComponents;

	@lombok.Builder
	@PersistenceConstructor
	public TeamGeocoding(final String teamId, final double lat, final double lng, final Map<String, String> addressComponents) {
		this.teamId = teamId;
		this.lat = lat;
		this.lng = lng;
		this.addressComponents = addressComponents;
	}

}