package pl.michal.olszewski.typer.match.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.michal.olszewski.typer.file.FileStorageProperties;
import pl.michal.olszewski.typer.file.FileStorageService;

class MatchLeagueFileAdapterTest {

  private MatchLeagueFileAdapter matchLeagueFileAdapter;
  private MatchLeagueFinder leagueFinder;

  @BeforeEach
  void setUp() {
    FileStorageProperties fileStorageProperties = new FileStorageProperties();
    fileStorageProperties.setUploadDir("uploads");
    MatchLeagueSaver matchLeagueSaver = new InMemoryMatchLeagueSaver();
    leagueFinder = new InMemoryMatchLeagueFinder();
    matchLeagueFileAdapter = new MatchLeagueFileAdapter(matchLeagueSaver, new FileStorageService(fileStorageProperties));
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