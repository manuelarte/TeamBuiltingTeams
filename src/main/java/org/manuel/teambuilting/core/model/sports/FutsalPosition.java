package org.manuel.teambuilting.core.model.sports;

import org.manuel.teambuilting.core.model.TeamSport;
import org.manuel.teambuilting.core.model.TeamSportPosition;

import lombok.Getter;

/**
 * 
 * @author Manuel Doncel Martos
 *
 */
@Getter
public enum FutsalPosition implements TeamSportPosition {

	GK("goalkeeper", 0.5, 0),
	LD("Left Defender", 0.25, 0.25),
	DF("Defender", 0.5, 0.25),
	RD("Right Defender", 1-LD.x, LD.y),
	LW("Left winger", 0.15, 0.5),
	RW("Right winger", 1-LW.x, LW.y),
	PV("Pivot", 1-DF.x, 1-DF.y),
	UNIVERSAL("Universal", 0.5, 0.5);
	
	private static final TeamSport SPORT = TeamSport.FUTSAL;
	
	private final String name;
	private final double x;
	private final double y;

	FutsalPosition(final String name, final double x, final double y) {
		this.name = name;
		this.x = x;
		this.y = y;
	}
	
	@Override
	public TeamSport sport() {
		return SPORT;
	}

	@Override
	public String getAbbreviation() {
		return name();
	}
	
	@Override
	public String getFullName() {
		return getName();
	}

	@Override
	public TeamSportPosition getEnumValue(final String positionName) {
		return FutsalPosition.valueOf(positionName);
	}
	
}
