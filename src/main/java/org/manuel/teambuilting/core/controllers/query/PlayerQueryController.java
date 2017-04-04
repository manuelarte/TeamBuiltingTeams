package org.manuel.teambuilting.core.controllers.query;

import org.manuel.teambuilting.core.model.Player;
import org.manuel.teambuilting.core.services.query.PlayerQueryService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/core/players")
public class PlayerQueryController extends AbstractQueryController<Player, String, PlayerQueryService> {

	public PlayerQueryController(final PlayerQueryService playerQueryService) {
		super(playerQueryService);
	}

	@RequestMapping(method = RequestMethod.GET)
	public Page<Player> findPlayerByName(@PageableDefault(page = 0, size = 20) final Pageable pageable,
		@RequestParam(value = "name", defaultValue = "") final String name) {
		Assert.notNull(name);
		return queryService.findPlayerByName(pageable, name);
	}

}
