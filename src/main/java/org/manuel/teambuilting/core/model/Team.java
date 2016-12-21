/**
 * 
 */
package org.manuel.teambuilting.core.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.mongodb.annotations.Immutable;

import java.util.Date;

import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Manuel Doncel Martos
 *
 */
@Immutable
@Document
@Data
@lombok.Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@JsonIgnoreProperties
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonDeserialize
public class Team {

	@Id
	private String id;

	@NotNull
	@Indexed
	private String name;
	private String location;
	@NotNull
	@Indexed
	private String sport;

	private String bio;
	private Date fromDate;
	private Date toDate;

	private String emblemLink;

	@PersistenceConstructor
	public Team(final String name, final String location, final String sport, final String bio, final Date fromDate, final Date toDate) {
		this.name = name;
		this.location = location;
		this.sport = sport;
		this.bio = bio;
		this.fromDate = fromDate;
		this.toDate = toDate;
	}

}
