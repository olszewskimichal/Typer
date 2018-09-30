package pl.michal.olszewski.typer.bet.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
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
        givenBets().buildNumberOfBetsForUserAndSave(2, 1L);

        ResponseEntity<List<BetResult>> userResults = betResultRestController.getUserResults(1L);

        assertThat(userResults.getStatusCodeValue()).isEqualTo(200);
        assertThat(userResults.getBody()).isNotEmpty().hasSize(2);
    }

    @Test
    void shouldThrowExceptionWhenBetByIdNotExists() {
        assertThrows(BetNotFoundException.class, () -> betResultRestController.getResultByBetId(2L));
    }

    @Test
    void shouldReturnBetResultById() {
        givenBets().buildBetWithIdAndSave(1L);

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
        givenBets().buildNumberOfBetsForMatchAndSave(2, 1L);

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
        givenBets().buildNumberOfBetsForMatchRoundAndSave(2, 1L);

        ResponseEntity<List<BetResult>> userResults = betResultRestController.getRoundResults(1L);

        assertThat(userResults.getStatusCodeValue()).isEqualTo(200);
        assertThat(userResults.getBody()).isNotEmpty().hasSize(2);
    }

    private BetFactory givenBets() {
        return new BetFactory(betSaver);
    }

}