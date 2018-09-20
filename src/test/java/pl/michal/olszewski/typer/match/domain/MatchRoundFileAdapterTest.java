package pl.michal.olszewski.typer.match.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.michal.olszewski.typer.file.FileStorageProperties;
import pl.michal.olszewski.typer.file.FileStorageService;

class MatchRoundFileAdapterTest {

  private MatchRoundFileAdapter matchLeagueFileAdapter;
  private MatchRoundFinder leagueFinder;
  private MatchLeagueSaver matchLeagueSaver;

  @BeforeEach
  void setUp() {
    FileStorageProperties fileStorageProperties = new FileStorageProperties();
    fileStorageProperties.setUploadDir("uploads");
    matchLeagueSaver = new InMemoryMatchLeagueSaver();
    MatchRoundSaver matchRoundSaver = new InMemoryMatchRoundSaver();
    MatchLeagueFinder matchLeagueFinder = new InMemoryMatchLeagueFinder();
    leagueFinder = new InMemoryMatchRoundFinder();

    matchLeagueFileAdapter = new MatchRoundFileAdapter(new MatchRoundCreator(matchLeagueFinder), matchRoundSaver, new FileStorageService(fileStorageProperties));

    matchRoundSaver.deleteAll();
    matchLeagueSaver.deleteAll();
  }


  @Test
  void shouldCreateOneMatchRoundFromXlsxFile() throws IOException {
    //given
    matchLeagueSaver.save(MatchLeague.builder().id(2L).build());
    Path file = Paths.get("testresources/matchRound.xlsx");

    //when
    matchLeagueFileAdapter.loadLeaguesFromFile(file);

    //then
    assertThat(leagueFinder.findAll()).isNotEmpty().hasSize(1);
  }

  @Test
  void shouldCreateOneMatchRoundFromXlsFile() throws IOException {
    matchLeagueSaver.save(MatchLeague.builder().id(2L).build());
    Path file = Paths.get("testresources/matchRound.xls");

    //when
    matchLeagueFileAdapter.loadLeaguesFromFile(file);

    //then
    assertThat(leagueFinder.findAll()).isNotEmpty().hasSize(1);
  }
}