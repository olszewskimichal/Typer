package pl.michal.olszewski.typer.match.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.File;
import java.io.IOException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MatchLeagueFileAdapterTest {

  private MatchLeagueFileAdapter matchLeagueFileAdapter;
  private MatchLeagueFinder leagueFinder;

  @BeforeEach
  void setUp() {
    MatchLeagueSaver matchLeagueSaver = new InMemoryMatchLeagueSaver();
    leagueFinder = new InMemoryMatchLeagueFinder();
    matchLeagueFileAdapter = new MatchLeagueFileAdapter(new MatchLeagueCreator(), matchLeagueSaver);
    matchLeagueSaver.deleteAll();
  }


  @Test
  void shouldCreateOneMatchLeagueFromXlsxFile() throws IOException {
    //given
    File file = new File("testresources/matchLeague.xlsx");

    //when
    matchLeagueFileAdapter.loadLeaguesFromFile(file);

    //then
    assertThat(leagueFinder.findAll()).isNotEmpty().hasSize(1);
  }

  @Test
  void shouldCreateOneMatchLeagueFromXlsFile() throws IOException {
    File file = new File("testresources/matchLeague.xls");

    //when
    matchLeagueFileAdapter.loadLeaguesFromFile(file);

    //then
    assertThat(leagueFinder.findAll()).isNotEmpty().hasSize(1);
  }
}