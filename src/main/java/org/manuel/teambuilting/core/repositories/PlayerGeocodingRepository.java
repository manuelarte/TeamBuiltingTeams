package org.manuel.teambuilting.core.repositories;

import org.bson.types.ObjectId;
import org.manuel.teambuilting.core.model.PlayerGeocoding;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author manuel.doncel.martos
 * @since 14-3-2017
 */
@Repository
public interface PlayerGeocodingRepository extends MongoRepository<PlayerGeocoding, ObjectId> {

    List<PlayerGeocoding> findByEntityId(String playerId);

}
