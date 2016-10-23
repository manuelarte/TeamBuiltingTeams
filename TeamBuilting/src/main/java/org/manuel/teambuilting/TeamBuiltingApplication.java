package org.manuel.teambuilting;

import java.time.LocalDate;
import java.time.Month;
import java.time.ZoneId;
import java.util.Date;

import org.manuel.teambuilting.dtos.PlayerDTO;
import org.manuel.teambuilting.dtos.PlayerToTeamDTO;
import org.manuel.teambuilting.dtos.TeamHistDTO;
import org.manuel.teambuilting.model.Player;
import org.manuel.teambuilting.model.PlayerId;
import org.manuel.teambuilting.model.TeamId;
import org.manuel.teambuilting.model.repository.PlayerRepository;
import org.manuel.teambuilting.model.repository.PlayerToTeamRepository;
import org.manuel.teambuilting.services.DTOSConverter;
import org.manuel.teambuilting.services.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TeamBuiltingApplication implements CommandLineRunner {

	@Autowired
	private PlayerRepository playerRepository;

	@Autowired
	private TeamService teamService;

	@Autowired
	private PlayerToTeamRepository playerToTeamRepository;

	@Autowired
	private DTOSConverter dtosConverter;

	public static void main(final String[] args) {
		SpringApplication.run(TeamBuiltingApplication.class, args);
	}

	@Override
	public void run(final String... args) throws Exception {
		createData();
	}

	private void createData() {
		final PlayerDTO javiLeon = createPlayer("Javier leon", "Soria, Castille and Leon, Spain");
		final PlayerDTO sanne = createPlayer("Sanne", "Amsterdam, Netherlands");
		final PlayerDTO mihaiDolghan = createPlayer("Mihai Dolghan", "Bucharest, Romania");
		final PlayerDTO oscar = createPlayer("Oscar", "Santander, Cantabria, Spain");
		final PlayerDTO borja = createPlayer("Borja Sacrist�n", "Madrid, Madrid, Spain");
		final PlayerDTO manu = createPlayer("Manuel Doncel Martos", "�beda, Ja�n, 23400 Spain");
		final PlayerDTO pedro = createPlayer("Pedro Dans", "Coru�a, Galicia, Spain");
		final PlayerDTO dennis = createPlayer("Dennis Bakker", "Madrid, Madrid, Spain");
		final PlayerDTO karim = createPlayer("Karim", "Guadalajara, Madrid, Spain");
		final PlayerDTO diego = createPlayer("Diego Ramonde", "Coru�a, Galicia, Spain");
		final PlayerDTO nelson = createPlayer("Nelson Alfonso", "Lisbon, Portugal");
		final PlayerDTO theo = createPlayer("Theodor Phantender", "Amsterdam, Netherlands");
		final PlayerDTO daan = createPlayer("Dann Farjon", "Amsterdam, Netherlands");
		final PlayerDTO tomasVirkick = createPlayer("Tomas Virkick", "Slovakia");
		final PlayerDTO tomasZ = createPlayer("Tomas Z", "Slovakia");
		final PlayerDTO kuba = createPlayer("Kuba", "Krakow, Poland");
		
		final Date startDevo2 = Date.from(LocalDate.of(1958, 1, 1).atStartOfDay(ZoneId.systemDefault()).toInstant());
		final String devoAddress = "Herman Bonpad 4, 1067 SN Amsterdam";
		final TeamHistDTO devo2 = createTeam("Devo2", devoAddress, startDevo2);
		
		// final Date startDevo2Fake = Date.from(LocalDate.of(1900, 1,
		// 1).atStartOfDay(ZoneId.systemDefault()).toInstant());
		// final TeamHistDTO previousDevo2 = teamHistService.saveTeamHist(new
		// TeamHistDTO.Builder(devo2).withId(null)
		// .withFromDate(startDevo2Fake)
		// .withToDate(startDevo2).build());
		
		final LocalDate september2015 = LocalDate.of(2015, Month.SEPTEMBER.getValue(), 1);
		playerToTeamRepository(manu.getId(), devo2.getTeamId(), september2015, null);
		playerToTeamRepository(javiLeon.getId(), devo2.getTeamId(), september2015, null);
		playerToTeamRepository(diego.getId(), devo2.getTeamId(), september2015, null);
		playerToTeamRepository(nelson.getId(), devo2.getTeamId(), september2015, null);

		playerToTeamRepository(sanne.getId(), devo2.getTeamId(), september2015.minusYears(1), null);
		playerToTeamRepository(mihaiDolghan.getId(), devo2.getTeamId(), september2015.minusYears(1), null);
		playerToTeamRepository(oscar.getId(), devo2.getTeamId(), september2015.minusYears(1), null);
		playerToTeamRepository(borja.getId(), devo2.getTeamId(), september2015.minusYears(1), null);
		playerToTeamRepository(pedro.getId(), devo2.getTeamId(), september2015.minusYears(1), null);
		playerToTeamRepository(dennis.getId(), devo2.getTeamId(), september2015.minusYears(1), null);
		playerToTeamRepository(karim.getId(), devo2.getTeamId(), september2015.minusYears(1), null);
		playerToTeamRepository(theo.getId(), devo2.getTeamId(), september2015.minusYears(1), null);
		playerToTeamRepository(daan.getId(), devo2.getTeamId(), september2015.minusYears(1), null);
		playerToTeamRepository(tomasVirkick.getId(), devo2.getTeamId(), september2015.minusYears(1), null);
		playerToTeamRepository(tomasZ.getId(), devo2.getTeamId(), september2015.minusYears(1), null);
		playerToTeamRepository(kuba.getId(), devo2.getTeamId(), september2015.minusYears(1), null);

	}

	private TeamHistDTO createTeam(final String name, final String location, final Date fromDate) {
		final TeamHistDTO teamHist = new TeamHistDTO.Builder().withName(name).withLocation(location)
				.withFromDate(fromDate).build();
		return teamService.createTeam(teamHist);
	}

	private PlayerDTO createPlayer(final String name, final String bornAddress) {
		final PlayerDTO player = new PlayerDTO.Builder().withName(name).withBornAddress(bornAddress).build();
		final Player saved = playerRepository.save(dtosConverter.toPlayer().apply(player));
		return dtosConverter.toPlayerDTO().apply(saved);
	}

	private void playerToTeamRepository(final PlayerId playerId, final TeamId teamId, final LocalDate startDate,
			final LocalDate endDate) {

		final PlayerToTeamDTO playerToTeam = new PlayerToTeamDTO.Builder().withPlayerId(playerId).withTeamId(teamId)
				.withStartDate(startDate).withEndDate(endDate).build();

		playerToTeamRepository.save(dtosConverter.toPlayerToTeam().apply(playerToTeam));

	}
}
