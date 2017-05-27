package org.manuel.teambuilting.teams.controllers.query;

import lombok.AllArgsConstructor;
import org.manuel.teambuilting.teams.model.UserData;
import org.manuel.teambuilting.teams.services.UserService;
import org.manuel.teambuilting.teams.util.Util;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Manuel Doncel Martos on 11/12/2016.
 */
@RestController
@RequestMapping("/teams/user")
@AllArgsConstructor
public class UserQueryController {

    private final UserService userService;
    private final Util util;

    @RequestMapping(method = RequestMethod.GET)
    public UserData getUserData() {
        final String userId = util.getUserProfile().get().getUserId();
        return userService.getOrCreateUserData(userId);
    }

}
