package pl.michal.olszewski.typer.bet.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.michal.olszewski.typer.bet.dto.BetNotCheckedException;
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
    givenBets().buildNumberOfBetsForUserAndStatus(2, 3L, BetStatus.IN_PROGRESS);
    givenBets().buildNumberOfBetsForUserAndStatus(2, 3L, BetStatus.CHECKED);

    List<BetResult> userResults = betResultService.getUserResults(3L);

    assertThat(userResults).hasSize(2);
  }

  @Test
  void shouldReturnEmptyResultListForUserWithoutBets() {

    List<BetResult> userResults = betResultService.getUserResults(2L);

    assertThat(userResults).hasSize(0);
  }

  @Test
  void shouldReturnBetResultByIdWhenResultIsChecked() {
    givenBets().buildBetWithIdAndStatus(4L, BetStatus.CHECKED);

    BetResult result = betResultService.getResultByBetId(4L);

    assertThat(result).isNotNull();
  }


  @Test
  void shouldThrowExceptionWhenBetByIdNotExists() {
    assertThrows(BetNotFoundException.class, () -> betResultService.getResultByBetId(5L));
  }

  @Test
  void shouldThrowExceptionWhenBetIsNotChecked() {
    givenBets().buildBetWithIdAndSave(6L);
    assertThrows(BetNotCheckedException.class, () -> betResultService.getResultByBetId(6L));
  }

  @Test
  void shouldReturnBetResultsForRound() {
    givenBets().buildNumberOfBetsForMatchRoundAndStatus(2, 3L, BetStatus.CHECKED);
    givenBets().buildNumberOfBetsForMatchRoundAndStatus(2, 3L, BetStatus.IN_PROGRESS);

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
    givenBets().buildNumberOfBetsForMatchAndStatus(2, 3L, BetStatus.IN_PROGRESS);
    givenBets().buildNumberOfBetsForMatchAndStatus(2, 3L, BetStatus.CHECKED);

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