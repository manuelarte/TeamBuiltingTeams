package org.manuel.teambuilting.core.model.sports;

import org.manuel.teambuilting.core.model.TeamSport;
import org.manuel.teambuilting.core.model.TeamSportPosition;

import lombok.Getter;

/**
 * The positions available as a football player
 *  The field is described like this
 *
 *     -----------(0.0, 1.0)------------
 *     |       |    |_______|   |       |
 *     |       |                |       |
 *     |       |________________|       |
 *     |              y                 |
 *     |              ^                 |
 *     |              |                 |
 * (0.5 , 0)     (0.5,0.5)->x       (1, 0)
 *     |                                |
 *     |                                |
 *     |       __________________       |
 *     ^       |                |       |
 *   y |       |     _______    |       |
 *     |       |    |       |   |       |
 *   (0,0)-->-------(0 ,0.5)------------
 *
 * @author Manuel Doncel Martos
 *
 */
@Getter
public enum FootballPosition implements TeamSportPosition {

	GK("Goalkeeper", 0.5, 0),
	LB("Left Back", 0.18, 0.25),
	LCB("Left Center Back", 0.4, 0.25),
	CB("Center-back", 0.5, 0.20),
	RCB("Right Center Back", 1.0 - LCB.x, LCB.y),
	RB("Right Back", 1-LB.x, LB.y),
	LM("Left Midfielder", 0.18, 0.5),
	LCM("Left Centre Midfield", 0.4, 0.5),
	CDM("Centre Defensive Midfield", 0.5, 0.4),
	CM("Centre Midfield (back and forward)", 0.5, 0.5),
	CAM("Centre Attacking  Midfield", CDM.x, 1-CDM.y),
	RCM("Right Centre Midfield", 1-LCM.x, LCM.y),
	RM("Right Midfield", 1-LM.x, LM.y),
	ST("Striker", 0.5, 0.75),
	CF("Center forward", 0.5, 0.9),
	LW("Left winger", 0.2, 0.8),
	RW("Right winger", 1-LW.x, LW.y);

	private static final TeamSport SPORT = TeamSport.FOOTBALL;
	
	private final String name;
	private final double x;
	private final double y;

	FootballPosition(final String name, final double x, final double y) {
		this.name = name;
		this.x = x;
		this.y = y;
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
	public String getFullName() {
		return getName();
	}

	@Override
	public TeamSport sport() {
		return SPORT;
	}
	
}
