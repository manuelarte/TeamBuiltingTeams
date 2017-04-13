package org.manuel.teambuilting.teams;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy
public class TeamBuiltingTeamsApplication {

	public static void main(final String[] args) {
		SpringApplication.run(TeamBuiltingTeamsApplication.class, args);
	}

}
