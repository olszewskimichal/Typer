package pl.michal.olszewski.typer.team.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import pl.michal.olszewski.typer.team.dto.TeamExistsException;
import pl.michal.olszewski.typer.team.dto.command.CreateNewTeam;

class TeamCreatorTest {

  private TeamCreator teamCreator = new TeamCreator(new InMemoryTeamFinder());
  private TeamSaver teamSaver = new InMemoryTeamSaver();

  @Test
  void shouldThrowExceptionWhenCommandIsNull() {
    assertThrows(NullPointerException.class, () -> teamCreator.from(null));
  }

  @Test
  void shouldThrowExceptionWhenNameIsNull() {
    //given
    //when
    //then
    assertThrows(IllegalArgumentException.class, () -> teamCreator.from(CreateNewTeam.builder().name(null).build()));
  }

  @Test
  void shouldThrowExceptionWhenTeamWithTheSameNameExists() {
    //given
    teamSaver.save(Team.builder().id(3L).name("nazwa").build());

    CreateNewTeam createNewUser = CreateNewTeam.builder().name("nazwa").build();
    //when
    //then
    assertThrows(TeamExistsException.class, () -> teamCreator.from(createNewUser));
  }

  @Test
  void shouldCreateNewTeam() {
    //given
    Team expected = Team.builder().name("name").build();

    CreateNewTeam createNewTeam = CreateNewTeam.builder().name("name").build();
    //when
    Team team = teamCreator.from(createNewTeam);
    //then
    assertThat(team).isEqualToComparingFieldByField(expected);
  }
}