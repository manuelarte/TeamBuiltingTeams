package org.manuel.teambuilting.messages;

import java.util.Date;

import javax.validation.constraints.NotNull;

import org.manuel.teambuilting.core.model.Team;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author Manuel Doncel Martos
 * @since 11/01/2017.
 */
@Data
@AllArgsConstructor
public class TeamCreatedMessage {

    @NotNull
    private final Team team;

    private final String userId;

    @NotNull
    private final Date date;
}
