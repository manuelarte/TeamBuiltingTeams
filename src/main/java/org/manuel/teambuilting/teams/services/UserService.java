package org.manuel.teambuilting.teams.services;

import java.util.HashSet;
import java.util.Optional;

import javax.inject.Inject;

import org.manuel.teambuilting.teams.model.UserData;
import org.manuel.teambuilting.teams.repositories.UserRepository;
import org.springframework.stereotype.Service;

/**
 * @author Manuel on 11/12/2016.
 */
@Service
public class UserService {

    private final UserRepository repository;

    @Inject
    public UserService(final UserRepository repository) {
        this.repository = repository;
    }

    public UserData getOrCreateUserData(final String userId) {
        final Optional<UserData> retrieved = getUserData(userId);
        return retrieved.isPresent() ? retrieved.get() : createUserData(userId, null, new HashSet<>());
    }

    private Optional<UserData> getUserData(final String userId) {
        return Optional.ofNullable(repository.findByUserId(userId));
    }

    private UserData createUserData(final String userId, final String playerId, HashSet<String> adminOfTeams) {
        final UserData userData = new UserData(userId, adminOfTeams);
        return repository.save(userData);
    }

    public UserData update(final UserData userData) {
        return repository.save(userData);
    }
}
