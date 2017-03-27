package org.manuel.teambuilting.messages;

import java.util.Date;

import javax.validation.constraints.NotNull;

import org.manuel.teambuilting.core.model.Player;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author Manuel Doncel Martos
 * @since 07/12/2016.
 */
@Data
@AllArgsConstructor
public class PlayerVisitedMessage {

    @NotNull
    private final Player player;

    private final String userId;

    @NotNull
    private final Date date;
}
