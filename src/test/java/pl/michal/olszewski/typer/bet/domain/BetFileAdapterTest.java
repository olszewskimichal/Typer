package pl.michal.olszewski.typer.bet.domain;

import static org.assertj.core.api.Java6Assertions.assertThat;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BetFileAdapterTest {

  private BetFileAdapter betFileAdapter;
  private BetFinder betFinder;

  @BeforeEach
  void setUp() {
    BetSaver betSaver = new InMemoryBetSaver();
    betFinder = new InMemoryBetFinder();
    betFileAdapter = new BetFileAdapter(new BetCreator(), betSaver);
    betSaver.deleteAll();
  }


  @Test
  void shouldCreateOneUserFromXlsxFile() throws IOException {
    //given
    Path file = Paths.get("testresources/betfile.xlsx");

    //when
    betFileAdapter.loadBetsFromFile(file);

    //then
    assertThat(betFinder.findAll()).isNotEmpty().hasSize(1);
  }

  @Test
  void shouldCreateOneBetFromXlsFile() throws IOException {
    Path file = Paths.get("testresources/betfile.xls");

    //when
    betFileAdapter.loadBetsFromFile(file);

    //then
    assertThat(betFinder.findAll()).isNotEmpty().hasSize(1);
  }

}