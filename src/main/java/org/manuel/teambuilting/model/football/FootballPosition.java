package org.manuel.teambuilting.model.football;

import org.manuel.teambuilting.model.TeamSport;
import org.manuel.teambuilting.model.TeamSportPosition;

import lombok.Getter;

/**
 * 
 * @author Manuel Doncel Martos
 *
 */
@Getter
public enum FootballPosition implements TeamSportPosition {

	GK("goalkeeper"),
	LB("Left Back"),
	LCB("Left Center Back"),
	CB("Center-back"),
	RCB("Right Center Back"),
	RB("Right Back"),
	LM("Left Midfielder"),
	LCM("Left Centre Midfield"),
	CDM("Centre Defensive Midfield"),
	CM("Centre Midfield (back and forward)"),
	CAM("Centre Attacking  Midfield"),
	RCM("Right Centre Midfield"),
	RM("Right Midfield"),
	ST("Striker"),
	CF("Center forward"),
	LW("Left winger"),
	RW("Right winger");

	private static final TeamSport SPORT = TeamSport.FOOTBALL;
	
	private final String name;

	FootballPosition(final String name) {
		this.name = name;
	}
	
	@Override
	public TeamSportPosition getEnumValue(final String positionName) {
		return FootballPosition.valueOf(positionName);
	}
	
	@Override
	public String getAbbreviation() {
		return name();
	}

	@Override
	public TeamSport sport() {
		return SPORT;
	}
	
}