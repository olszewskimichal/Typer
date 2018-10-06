package pl.michal.olszewski.typer.team.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import pl.michal.olszewski.typer.team.dto.TeamNotFoundException;
import pl.michal.olszewski.typer.team.dto.command.CreateNewTeam;
import pl.michal.olszewski.typer.team.dto.read.TeamInfo;

class TeamRestControllerUnitTest {

  private TeamRestController teamRestController;
  private TeamSaver teamSaver = new InMemoryTeamSaver();
  private TeamFinder teamFinder = new InMemoryTeamFinder();

  @BeforeEach
  void setUp() {
    teamRestController = new TeamRestController(teamFinder, teamSaver);
  }

  @Test
  void shouldReturnNotFoundWhenTeamByIdNotExist() {
    assertThrows(TeamNotFoundException.class, () -> teamRestController.getTeamInfoById(254L));
  }

  @Test
  void shouldReturnTeamInfoWhenMatchByIdExists() {
    Team team = Team.builder().id(254L).name("nazwa").build();
    teamSaver.save(team);

    ResponseEntity<TeamInfo> betInfoById = teamRestController.getTeamInfoById(254L);
    assertThat(betInfoById.getStatusCodeValue()).isEqualTo(200);
    assertThat(betInfoById.getBody()).isNotNull();
    assertThat(betInfoById.getBody().getId()).isEqualTo(254L);

  }

  @Test
  void shouldCreateNewTeam() {
    CreateNewTeam createNewTeam = CreateNewTeam.builder().name("name").build();
    ResponseEntity<String> responseEntity = teamRestController.createNewTeam(createNewTeam);

    assertThat(responseEntity.getStatusCodeValue()).isEqualTo(201);
    assertThat(responseEntity.getBody()).isNull();
  }

  @AfterEach
  void removeAll() {
    teamSaver.deleteAll();
  }


}