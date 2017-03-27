package org.manuel.teambuilting.messages;

import java.util.Date;

import javax.validation.constraints.NotNull;

import org.manuel.teambuilting.core.model.Team;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author Manuel Doncel Martos
 * @since 07/12/2016.
 */
@Data
@AllArgsConstructor
public class TeamVisitedMessage {

    @NotNull
    private final Team team;

    private final String userId;

    @NotNull
    private final Date date;
}
