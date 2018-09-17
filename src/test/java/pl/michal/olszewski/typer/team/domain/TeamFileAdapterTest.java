package pl.michal.olszewski.typer.team.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.File;
import java.io.IOException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TeamFileAdapterTest {

  private TeamFileAdapter teamFileAdapter;
  private TeamFinder teamFinder;

  @BeforeEach
  void setUp() {
    TeamSaver teamSaver = new InMemoryTeamSaver();
    teamFinder = new InMemoryTeamFinder();
    teamFileAdapter = new TeamFileAdapter(new TeamCreator(teamFinder), teamSaver);
    teamSaver.deleteAll();
  }


  @Test
  void shouldCreateOneTeamFromXlsxFile() throws IOException {
    //given
    File file = new File("testresources/team.xlsx");

    //when
    teamFileAdapter.loadTeamsFromFile(file);

    //then
    assertThat(teamFinder.findAll()).isNotEmpty().hasSize(1);
  }

  @Test
  void shouldCreateOneTeamFromXlsFile() throws IOException {
    File file = new File("testresources/team.xls");

    //when
    teamFileAdapter.loadTeamsFromFile(file);

    //then
    assertThat(teamFinder.findAll()).isNotEmpty().hasSize(1);
  }

}