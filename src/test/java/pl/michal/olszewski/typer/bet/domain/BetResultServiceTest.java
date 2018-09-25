package pl.michal.olszewski.typer.bet.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.michal.olszewski.typer.bet.dto.read.BetResult;

class BetResultServiceTest {

  private BetFinder betFinder = new InMemoryBetFinder();
  private BetResultService betResultService;

  @BeforeEach
  void configureSystemUnderTests() {
    betResultService = new BetResultService(betFinder);
    givenBets()
        .deleteAll();
  }

  @Test
  void shouldReturnBetResultsForUser() {
    givenBets().buildNumberOfBetsForUserAndSave(2, 3L);

    List<BetResult> userResults = betResultService.getUserResults(3L);

    assertThat(userResults).hasSize(2);
  }

  @Test
  void shouldReturnEmptyResultListForUserWithoutBets() {

    List<BetResult> userResults = betResultService.getUserResults(2L);

    assertThat(userResults).hasSize(0);
  }

  private BetFactory givenBets() {
    return new BetFactory(new InMemoryBetSaver());
  }

}