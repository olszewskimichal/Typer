package pl.michal.olszewski.typer.bet.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import pl.michal.olszewski.typer.bet.dto.read.BetResult;

class BetResultRestControllerUnitTest {

  private BetResultRestController betResultRestController;

  @BeforeEach
  void setUp() {
    BetFinder betFinder = new InMemoryBetFinder();
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
    givenBets().buildNumberOfBetsForUserAndSave(2, 1L);

    ResponseEntity<List<BetResult>> userResults = betResultRestController.getUserResults(1L);

    assertThat(userResults.getStatusCodeValue()).isEqualTo(200);
    assertThat(userResults.getBody()).isNotEmpty().hasSize(2);
  }

  private BetFactory givenBets() {
    return new BetFactory(new InMemoryBetSaver());
  }

}