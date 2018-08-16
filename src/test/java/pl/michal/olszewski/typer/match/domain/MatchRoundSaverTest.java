package pl.michal.olszewski.typer.match.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import pl.michal.olszewski.typer.RepositoryTestBase;

class MatchRoundSaverTest extends RepositoryTestBase {

  @Autowired
  private MatchRoundFinder finder;

  @Autowired
  private MatchRoundSaver saver;

  @Test
  void shouldSaveBetInDB() {
    //given
    MatchRound matchRound = MatchRound.builder().build();
    assertThat(finder.findAll()).hasSize(0);

    //when
    saver.save(matchRound);
    List<MatchRound> all = finder.findAll();

    //then
    assertThat(all).hasSize(1);
  }

  @Test
  void shouldDeleteAllFromDB() {
    //given
    MatchRound matchRound = MatchRound.builder().build();
    saver.save(matchRound);
    assertThat(finder.findAll()).hasSize(1);

    //when
    saver.deleteAll();

    //then
    assertThat(finder.findAll()).hasSize(0);
  }


}