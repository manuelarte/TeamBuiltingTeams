package org.manuel.teambuilting.core.repositories;

import java.util.Collection;

/**
 * @author manuel.doncel.martos
 * @since 5-4-2017
 */
public interface PlayerDependentRepository<Entity, ID> {

	Collection<Entity> findByPlayerId(String playerId);
}