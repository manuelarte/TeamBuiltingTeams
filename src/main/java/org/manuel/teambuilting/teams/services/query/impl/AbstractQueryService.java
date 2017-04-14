package org.manuel.teambuilting.teams.services.query.impl;

import java.io.Serializable;
import java.util.Optional;

import org.manuel.teambuilting.core.services.query.BaseQueryService;
import org.springframework.data.repository.CrudRepository;

/**
 * @author manuel.doncel.martos
 * @since 12-3-2017
 */
public abstract class AbstractQueryService<Entity, ID extends Serializable, Repository extends CrudRepository<Entity, ID>> implements
	BaseQueryService<Entity, ID> {

	protected final Repository repository;

	public AbstractQueryService(final Repository repository) {
		this.repository = repository;
	}

	@Override
	public Optional<Entity> findOne(final ID id) {
		final Optional<Entity> retrieved = Optional.ofNullable(repository.findOne(id));
		postFindOne(retrieved);
		return retrieved;
	}

	void postFindOne(final Optional<Entity> entity) {}

}
