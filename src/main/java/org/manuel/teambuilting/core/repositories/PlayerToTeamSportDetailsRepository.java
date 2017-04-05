package org.manuel.teambuilting.core.repositories;

import org.manuel.teambuilting.core.model.PlayerToTeamSportDetails;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayerToTeamSportDetailsRepository extends PlayerDependentRepository<PlayerToTeamSportDetails, String>, MongoRepository<PlayerToTeamSportDetails, String> {

	PlayerToTeamSportDetails findByPlayerIdAndSportIgnoringCase(String playerId, String sport);

}
