package org.manuel.teambuilting.teams.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public interface TeamSportPosition {

	String getAbbreviation();

	/**
	 * The full name of the sport
	 */
	String getFullName();

	/**
	 * The position x of the player
	 * @return the position x of the player
	 */
	double getX();

	/**
	 * The position y of the player
	 * @return the position y of the player
	 */
	double getY();

	@JsonIgnore
	TeamSportPosition getEnumValue(String positionName);

	@JsonIgnore
	TeamSport sport();

}
