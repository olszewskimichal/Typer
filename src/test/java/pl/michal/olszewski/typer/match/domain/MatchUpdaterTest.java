package pl.michal.olszewski.typer.match.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import pl.michal.olszewski.typer.match.dto.MatchNotFoundException;
import pl.michal.olszewski.typer.match.dto.command.CancelMatch;
import pl.michal.olszewski.typer.match.dto.command.FinishMatch;

class MatchUpdaterTest {

  private MatchFinder matchFinder = new InMemoryMatchFinder();

  private MatchUpdater matchUpdater = new MatchUpdater(matchFinder);

  @Test
  void shouldThrowExceptionWhenCancellingCommandIsNull() {
    assertThrows(NullPointerException.class, () -> matchUpdater.cancelMatch(null));
  }

  @Test
  void shouldThrowExceptionWhenCancelledMatchIdIsNull() {
    CancelMatch cancelMatch = CancelMatch
        .builder()
        .matchId(null)
        .build();

    assertThrows(NullPointerException.class, () -> matchUpdater.cancelMatch(cancelMatch));
  }

  @Test
  void shouldThrowExceptionWhenCancelledMatchIsNotFound() {
    CancelMatch cancelMatch = CancelMatch
        .builder()
        .matchId(1L)
        .build();

    assertThrows(MatchNotFoundException.class, () -> matchUpdater.cancelMatch(cancelMatch));
  }

  @Test
  void shouldCancelMatchWhenCommandIsValidAndMatchIsFound() {

    CancelMatch cancelMatch = CancelMatch
        .builder()
        .matchId(1L)
        .build();

    Match match = matchUpdater.cancelMatch(cancelMatch);

    assertThat(match).isNotNull();
    assertThat(match.getMatchStatus()).isEqualTo(MatchStatus.CANCELED);
  }

  @Test
  void shouldThrowExceptionWhenFinishedMatchCommandIsNull() {
    assertThrows(NullPointerException.class, () -> matchUpdater.finishMatch(null));
  }

  @Test
  void shouldThrowExceptionWhenFinishedMatchIdIsNull() {
    FinishMatch finishMatch = FinishMatch
        .builder()
        .matchId(null)
        .build();

    assertThrows(NullPointerException.class, () -> matchUpdater.finishMatch(finishMatch));
  }

  @Test
  void shouldThrowExceptionWhenFinishedMatchIsNotFound() {
    FinishMatch finishMatch = FinishMatch
        .builder()
        .matchId(1L)
        .build();

    assertThrows(MatchNotFoundException.class, () -> matchUpdater.finishMatch(finishMatch));
  }

  @Test
  void shouldFinishMatchWhenCommandIsValidAndMatchIsFound() {

    FinishMatch finishMatch = FinishMatch
        .builder()
        .matchId(1L)
        .build();

    Match match = matchUpdater.finishMatch(finishMatch);

    assertThat(match).isNotNull();
    assertThat(match.getMatchStatus()).isEqualTo(MatchStatus.FINISHED);
  }

}