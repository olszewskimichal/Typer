package pl.michal.olszewski.typer.match.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.File;
import java.io.IOException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MatchRoundFileAdapterTest {

  private MatchRoundFileAdapter matchLeagueFileAdapter;
  private MatchRoundFinder leagueFinder;
  private MatchLeagueSaver matchLeagueSaver;

  @BeforeEach
  void setUp() {
    matchLeagueSaver = new InMemoryMatchLeagueSaver();
    MatchRoundSaver matchRoundSaver = new InMemoryMatchRoundSaver();
    MatchLeagueFinder matchLeagueFinder = new InMemoryMatchLeagueFinder();
    leagueFinder = new InMemoryMatchRoundFinder();
    
    matchLeagueFileAdapter = new MatchRoundFileAdapter(new MatchRoundCreator(matchLeagueFinder), matchRoundSaver);

    matchRoundSaver.deleteAll();
    matchLeagueSaver.deleteAll();
  }


  @Test
  void shouldCreateOneMatchRoundFromXlsxFile() throws IOException {
    //given
    matchLeagueSaver.save(MatchLeague.builder().id(2L).build());
    File file = new File("testresources/matchRound.xlsx");

    //when
    matchLeagueFileAdapter.loadLeaguesFromFile(file);

    //then
    assertThat(leagueFinder.findAll()).isNotEmpty().hasSize(1);
  }

  @Test
  void shouldCreateOneMatchRoundFromXlsFile() throws IOException {
    matchLeagueSaver.save(MatchLeague.builder().id(2L).build());
    File file = new File("testresources/matchRound.xls");

    //when
    matchLeagueFileAdapter.loadLeaguesFromFile(file);

    //then
    assertThat(leagueFinder.findAll()).isNotEmpty().hasSize(1);
  }
}