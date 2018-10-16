package pl.michal.olszewski.typer.match.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import pl.michal.olszewski.typer.RepositoryTestBase;

class MatchLeagueSaverTest extends RepositoryTestBase {

  @Autowired
  private MatchLeagueFinder finder;

  @Autowired
  private MatchLeagueSaver saver;

  @Test
  void shouldSaveBetInDB() {
    //given
    MatchLeague matchLeague = MatchLeague.builder().build();
    assertThat(finder.findAll()).hasSize(0);

    //when
    saver.save(matchLeague);
    List<MatchLeague> all = finder.findAll();

    //then
    assertThat(all).hasSize(1);
  }

  @Test
  void shouldDeleteAllFromDB() {
    //given
    MatchLeague matchLeague = MatchLeague.builder().build();
    saver.save(matchLeague);
    assertThat(finder.findAll()).hasSize(1);

    //when
    saver.deleteAll();

    //then
    assertThat(finder.findAll()).hasSize(0);
  }


}