package org.manuel.teambuilting.teams.model;

import org.manuel.teambuilting.teams.model.sports.FootballPosition;
import org.manuel.teambuilting.teams.model.sports.FutsalPosition;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;

@Getter
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum TeamSport {

	FOOTBALL("Football", FootballPosition.values()),
	FUTSAL("Futsal", FutsalPosition.values());

	private final String name;
	private final TeamSportPosition[] sportPositions;
	
	TeamSport(final String name, final TeamSportPosition[] sportPositions) {
		this.name = name;
		this.sportPositions = sportPositions;
	}
	
}
