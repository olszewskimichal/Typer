package pl.michal.olszewski.typer.match.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import pl.michal.olszewski.typer.RepositoryTestBase;

class MatchSaverTest extends RepositoryTestBase {

  @Autowired
  private MatchFinder finder;

  @Autowired
  private MatchSaver saver;

  @Test
  void shouldSaveBetInDB() {
    //given
    Match match = Match.builder().build();
    assertThat(finder.findAll()).hasSize(0);

    //when
    saver.save(match);
    List<Match> all = finder.findAll();

    //then
    assertThat(all).hasSize(1);
  }

  @Test
  void shouldDeleteAllFromDB() {
    //given
    Match match = Match.builder().build();
    saver.save(match);
    assertThat(finder.findAll()).hasSize(1);

    //when
    saver.deleteAll();

    //then
    assertThat(finder.findAll()).hasSize(0);
  }


}