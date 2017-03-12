package org.manuel.teambuilting.core.controllers.query;

import static org.manuel.teambuilting.core.exceptions.ErrorCode.ID_NOT_FOUND;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.Optional;

import javax.inject.Inject;

import org.manuel.teambuilting.core.exceptions.ValidationRuntimeException;
import org.manuel.teambuilting.core.services.query.AbstractQueryService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author manuel.doncel.martos
 * @since 12-3-2017
 */
public abstract class AbstractQueryController<Entity, ID extends Serializable, QueryService extends AbstractQueryService<Entity, ID, ?>> implements
	InitializingBean {

	private final QueryService queryService;

	private Class<Entity> entityClass;

	@Inject
	public AbstractQueryController(QueryService queryService) {
		this.queryService = queryService;
	}

	@Override
	@SuppressWarnings("unchecked")
	public void afterPropertiesSet() throws Exception {
		this.entityClass = (Class<Entity>) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
	}

	@RequestMapping(path = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<Entity> findOne(@PathVariable("id") final ID id) {
		Assert.notNull(id);
		final Optional<Entity> entity = queryService.findOne(id);
		if (entity.isPresent()) {
			return ResponseEntity.ok(entity.get());
		}
		throw new ValidationRuntimeException(ID_NOT_FOUND.getErrorCode(), ID_NOT_FOUND.getMessage(getClassSimpleName(), id),
			ID_NOT_FOUND.getMessage(getClassSimpleName(), id));
	}

	private String getClassSimpleName() {
		return entityClass.getSimpleName();
	}

}
