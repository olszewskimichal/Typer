package pl.michal.olszewski.typer.match.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MatchFileAdapterTest {

  private MatchFileAdapter matchFileAdapter;
  private MatchFinder matchFinder;
  private InMemoryMatchRoundFinder inMemoryMatchRoundFinder = new InMemoryMatchRoundFinder();
  private MatchSaver matchSaver = new InMemoryMatchSaver();

  @BeforeEach
  void setUp() {
    matchSaver.deleteAll();
    MatchRoundSaver matchRoundSaver = new InMemoryMatchRoundSaver();
    matchRoundSaver.save(MatchRound.builder().id(1L).build());
    matchFinder = new InMemoryMatchFinder();
    matchFileAdapter = new MatchFileAdapter(new MatchCreator(inMemoryMatchRoundFinder), matchSaver);
  }


  @Test
  void shouldCreateOneMatchLeagueFromXlsxFile() throws IOException {
    //given
    Path file = Paths.get("testresources/match.xlsx");

    //when
    matchFileAdapter.loadMatchesFromFile(file);

    //then
    assertThat(matchFinder.findAll()).isNotEmpty().hasSize(1);
  }

  @Test
  void shouldCreateOneMatchLeagueFromXlsFile() throws IOException {
    Path file = Paths.get("testresources/match.xls");

    //when
    matchFileAdapter.loadMatchesFromFile(file);

    //then
    assertThat(matchFinder.findAll()).isNotEmpty().hasSize(1);
  }
}