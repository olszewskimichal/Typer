package pl.michal.olszewski.typer.bet.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import pl.michal.olszewski.typer.bet.dto.BetNotCheckedException;
import pl.michal.olszewski.typer.bet.dto.BetNotFoundException;
import pl.michal.olszewski.typer.bet.dto.read.BetResult;

class BetResultRestControllerUnitTest {

  private BetResultRestController betResultRestController;
  private BetSaver betSaver = new InMemoryBetSaver();
  private BetFinder betFinder = new InMemoryBetFinder();

  @BeforeEach
  void setUp() {
    givenBets().deleteAll();
    betResultRestController = new BetResultRestController(new BetResultService(betFinder));
  }

  @AfterEach
  void removeAll() {
    givenBets().deleteAll();
  }


  @Test
  void shouldReturnEmptyListWhenResultByUserNotExists() {
    ResponseEntity<List<BetResult>> userResults = betResultRestController.getUserResults(1L);

    assertThat(userResults.getStatusCodeValue()).isEqualTo(200);
    assertThat(userResults.getBody()).hasSize(0).isEmpty();
  }

  @Test
  void shouldReturnResultListWhenResultByUserExists() {
    givenBets().buildNumberOfBetsForUserAndStatus(2, 1L, BetStatus.CHECKED);
    givenBets().buildNumberOfBetsForUserAndStatus(2, 1L, BetStatus.IN_PROGRESS);

    ResponseEntity<List<BetResult>> userResults = betResultRestController.getUserResults(1L);

    assertThat(userResults.getStatusCodeValue()).isEqualTo(200);
    assertThat(userResults.getBody()).isNotEmpty().hasSize(2);
  }

  @Test
  void shouldThrowExceptionWhenBetByIdNotExists() {
    assertThrows(BetNotFoundException.class, () -> betResultRestController.getResultByBetId(2L));
  }

  @Test
  void shouldThrowExceptionWhenBetByIdIsNotChecked() {
    givenBets().buildBetWithIdAndStatus(2L, BetStatus.IN_PROGRESS);

    assertThrows(BetNotCheckedException.class, () -> betResultRestController.getResultByBetId(2L));
  }

  @Test
  void shouldReturnBetResultById() {
    givenBets().buildBetWithIdAndStatus(1L, BetStatus.CHECKED);

    ResponseEntity<BetResult> resultByBetId = betResultRestController.getResultByBetId(1L);

    assertThat(resultByBetId.getStatusCodeValue()).isEqualTo(200);
    assertThat(resultByBetId.getBody()).isNotNull();
    assertThat(resultByBetId.getBody().getBetId()).isEqualTo(1L);
  }

  @Test
  void shouldReturnEmptyListWhenResultByMatchNotExists() {
    ResponseEntity<List<BetResult>> userResults = betResultRestController.getMatchResults(1L);

    assertThat(userResults.getStatusCodeValue()).isEqualTo(200);
    assertThat(userResults.getBody()).hasSize(0).isEmpty();
  }

  @Test
  void shouldReturnResultListWhenResultByMatchExists() {
    givenBets().buildNumberOfBetsForMatchAndStatus(2, 1L, BetStatus.CHECKED);
    givenBets().buildNumberOfBetsForMatchAndStatus(2, 1L, BetStatus.IN_PROGRESS);

    ResponseEntity<List<BetResult>> userResults = betResultRestController.getMatchResults(1L);

    assertThat(userResults.getStatusCodeValue()).isEqualTo(200);
    assertThat(userResults.getBody()).isNotEmpty().hasSize(2);
  }

  @Test
  void shouldReturnEmptyListWhenResultByRoundNotExists() {
    ResponseEntity<List<BetResult>> userResults = betResultRestController.getRoundResults(1L);

    assertThat(userResults.getStatusCodeValue()).isEqualTo(200);
    assertThat(userResults.getBody()).hasSize(0).isEmpty();
  }

  @Test
  void shouldReturnResultListWhenResultByRoundExists() {
    givenBets().buildNumberOfBetsForMatchRoundAndStatus(2, 1L, BetStatus.CHECKED);
    givenBets().buildNumberOfBetsForMatchRoundAndStatus(2, 1L, BetStatus.IN_PROGRESS);

    ResponseEntity<List<BetResult>> userResults = betResultRestController.getRoundResults(1L);

    assertThat(userResults.getStatusCodeValue()).isEqualTo(200);
    assertThat(userResults.getBody()).isNotEmpty().hasSize(2);
  }

  private BetFactory givenBets() {
    return new BetFactory(betSaver);
  }

}