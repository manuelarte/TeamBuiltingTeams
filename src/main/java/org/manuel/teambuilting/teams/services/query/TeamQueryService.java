package org.manuel.teambuilting.teams.services.query;

import org.manuel.teambuilting.core.services.query.BaseQueryService;
import org.manuel.teambuilting.teams.model.Team;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * @author manuel.doncel.martos
 * @since 14-3-2017
 */
public interface TeamQueryService extends BaseQueryService<Team, String> {


	Page<Team> findTeamBy(Pageable pageable, String sport, String name);

}
