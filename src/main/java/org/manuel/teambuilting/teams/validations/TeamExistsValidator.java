package org.manuel.teambuilting.teams.validations;

import java.util.Optional;

import javax.inject.Inject;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.manuel.teambuilting.teams.model.Team;
import org.manuel.teambuilting.teams.repositories.TeamRepository;
import org.springframework.stereotype.Component;

/**
 * @author Manuel on 12/12/2016.
 */
@Component
public class TeamExistsValidator implements ConstraintValidator<TeamExists, String> {

    private final TeamRepository teamRepository;

    @Inject
    public TeamExistsValidator(final TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

    @Override
    public void initialize(final TeamExists constraintAnnotation) {

    }

    @Override
    public boolean isValid(final String teamId, final ConstraintValidatorContext context) {
        final Optional<Team> retrieved = Optional.ofNullable(teamRepository.findOne(teamId));
        return retrieved.isPresent();
    }
}
