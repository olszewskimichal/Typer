package pl.michal.olszewski.typer.match.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
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
    Path file = Paths.get("testresources/matchLeague.xlsx");

    //when
    matchLeagueFileAdapter.loadLeaguesFromFile(file);

    //then
    assertThat(leagueFinder.findAll()).isNotEmpty().hasSize(1);
  }

  @Test
  void shouldCreateOneMatchLeagueFromXlsFile() throws IOException {
    Path file = Paths.get("testresources/matchLeague.xls");

    //when
    matchLeagueFileAdapter.loadLeaguesFromFile(file);

    //then
    assertThat(leagueFinder.findAll()).isNotEmpty().hasSize(1);
  }
}