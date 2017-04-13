package org.manuel.teambuilting.teams.repositories;

import java.util.List;

import org.bson.types.ObjectId;
import org.manuel.teambuilting.teams.model.TeamGeocoding;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * @author manuel.doncel.martos
 * @since 14-3-2017
 */
@Repository
public interface TeamGeocodingRepository extends MongoRepository<TeamGeocoding, ObjectId> {

    List<TeamGeocoding> findByTeamId(String teamId);

}
