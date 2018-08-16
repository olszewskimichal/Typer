package pl.michal.olszewski.typer.bet.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import pl.michal.olszewski.typer.RepositoryTestBase;

class BetSaverTest extends RepositoryTestBase {

  @Autowired
  private BetFinder betFinder;

  @Autowired
  private BetSaver betSaver;

  @Test
  void shouldSaveBetInDB() {
    //given
    Bet bet = Bet.builder().matchId(2L).build();
    List<Bet> betForMatchBeforePersist = betFinder.findAllBetForMatch(2L);
    assertThat(betForMatchBeforePersist).hasSize(0);

    //when
    betSaver.save(bet);
    List<Bet> betForMatch = betFinder.findAllBetForMatch(2L);

    //then
    assertThat(betForMatch).hasSize(1);
  }


}