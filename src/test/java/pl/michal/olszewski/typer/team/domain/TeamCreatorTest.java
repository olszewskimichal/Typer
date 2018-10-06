package pl.michal.olszewski.typer.team.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import pl.michal.olszewski.typer.team.dto.TeamExistsException;
import pl.michal.olszewski.typer.team.dto.command.CreateNewTeam;

class TeamCreatorTest {

  private TeamFinder teamFinder = new InMemoryTeamFinder();
  private TeamSaver teamSaver = new InMemoryTeamSaver();

  @Test
  void shouldThrowExceptionWhenCommandIsNull() {
    assertThrows(NullPointerException.class, () -> TeamCreator.from(null, teamFinder));
  }

  @Test
  void shouldThrowExceptionWhenNameIsNull() {
    //given
    //when
    //then
    assertThrows(IllegalArgumentException.class, () -> TeamCreator.from(CreateNewTeam.builder().name(null).build(), teamFinder));
  }

  @Test
  void shouldThrowExceptionWhenTeamWithTheSameNameExists() {
    //given
    teamSaver.save(Team.builder().id(3L).name("nazwa").build());

    CreateNewTeam createNewUser = CreateNewTeam.builder().name("nazwa").build();
    //when
    //then
    assertThrows(TeamExistsException.class, () -> TeamCreator.from(createNewUser, teamFinder));
  }

  @Test
  void shouldCreateNewTeam() {
    //given
    Team expected = Team.builder().name("name").build();

    CreateNewTeam createNewTeam = CreateNewTeam.builder().name("name").build();
    //when
    Team team = TeamCreator.from(createNewTeam, teamFinder);
    //then
    assertThat(team).isEqualToComparingFieldByField(expected);
  }
}