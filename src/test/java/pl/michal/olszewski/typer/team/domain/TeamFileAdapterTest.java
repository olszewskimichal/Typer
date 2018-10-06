package pl.michal.olszewski.typer.team.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.michal.olszewski.typer.file.FileStorageProperties;
import pl.michal.olszewski.typer.file.FileStorageService;

class TeamFileAdapterTest {

  private TeamFileAdapter teamFileAdapter;
  private TeamFinder teamFinder;

  @BeforeEach
  void setUp() {
    FileStorageProperties fileStorageProperties = new FileStorageProperties();
    fileStorageProperties.setUploadDir("uploads");
    TeamSaver teamSaver = new InMemoryTeamSaver();
    teamFinder = new InMemoryTeamFinder();
    teamFileAdapter = new TeamFileAdapter(teamFinder, teamSaver, new FileStorageService(fileStorageProperties));
    teamSaver.deleteAll();
  }


  @Test
  void shouldCreateOneTeamFromXlsxFile() throws IOException {
    //given
    Path file = Paths.get("testresources/team.xlsx");

    //when
    teamFileAdapter.loadTeamsFromFile(file);

    //then
    assertThat(teamFinder.findAll()).isNotEmpty().hasSize(1);
  }

  @Test
  void shouldCreateOneTeamFromXlsFile() throws IOException {
    Path file = Paths.get("testresources/team.xls");

    //when
    teamFileAdapter.loadTeamsFromFile(file);

    //then
    assertThat(teamFinder.findAll()).isNotEmpty().hasSize(1);
  }

}