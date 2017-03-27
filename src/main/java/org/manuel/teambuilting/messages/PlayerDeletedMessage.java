package org.manuel.teambuilting.messages;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.manuel.teambuilting.core.model.Player;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * Event Message to show that a player was deleted
 *
 * @author Manuel Doncel Martos
 * @since 07/12/2016.
 */
@Data
@AllArgsConstructor
@Builder
public class PlayerDeletedMessage {

    public static final String ROUTING_KEY = "player.deleted";

    @NotNull
    private final Player player;

    @NotNull
    private final String userId;

    @NotNull
    private final Date date;

    @JsonIgnore
    public String getRoutingKey() {
        return ROUTING_KEY;
    }
}
