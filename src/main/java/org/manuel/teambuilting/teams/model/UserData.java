package org.manuel.teambuilting.teams.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;
import java.util.Set;

/**
 * @author Manuel on 11/12/2016.
 */
@Document
@Builder(toBuilder = true)
@Data
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class UserData {

    @Id
    private String id;

    @NotNull
    @Indexed
    private String userId;

    @NotNull
    private Set<String> adminOfTeams;

    @PersistenceConstructor
    public UserData(final String userId, final Set<String> adminOfTeams) {
        this.userId = userId;
        this.adminOfTeams = adminOfTeams;
    }

    public boolean addTeamAdminByUser(final String teamId) {
        return adminOfTeams.add(teamId);
    }

}
