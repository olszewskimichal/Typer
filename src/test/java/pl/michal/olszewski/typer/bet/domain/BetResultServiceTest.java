package pl.michal.olszewski.typer.bet.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.michal.olszewski.typer.bet.dto.BetNotFoundException;
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

  @Test
  void shouldReturnBetResultById() {
    givenBets().buildBetWithIdAndSave(4L);

    BetResult result = betResultService.getResultByBetId(4L);

    assertThat(result).isNotNull();
  }


  @Test
  void shouldThrowExceptionWhenBetByIdNotExists() {
    assertThrows(BetNotFoundException.class, () -> betResultService.getResultByBetId(5L));
  }

  @Test
  void shouldReturnBetResultsForRound() {
    givenBets().buildNumberOfBetsForMatchRoundAndSave(2, 3L);

    List<BetResult> userResults = betResultService.getBetResultsForRound(3L);

    assertThat(userResults).hasSize(2);
  }

  @Test
  void shouldReturnEmptyResultListForRoundWithoutBets() {

    List<BetResult> userResults = betResultService.getBetResultsForRound(2L);

    assertThat(userResults).hasSize(0);
  }

  @Test
  void shouldReturnBetResultsForMatch() {
    givenBets().buildNumberOfBetsForMatchAndSave(2, 3L);

    List<BetResult> userResults = betResultService.getBetResultsForMatch(3L);

    assertThat(userResults).hasSize(2);
  }

  @Test
  void shouldReturnEmptyResultListForMatchWithoutBets() {

    List<BetResult> userResults = betResultService.getBetResultsForMatch(2L);

    assertThat(userResults).hasSize(0);
  }

  private BetFactory givenBets() {
    return new BetFactory(new InMemoryBetSaver());
  }

}