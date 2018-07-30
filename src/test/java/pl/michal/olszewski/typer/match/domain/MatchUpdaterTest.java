package pl.michal.olszewski.typer.match.domain;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import pl.michal.olszewski.typer.match.dto.MatchNotFoundException;
import pl.michal.olszewski.typer.match.dto.command.CancelMatch;

class MatchUpdaterTest {

  private MatchUpdater matchUpdater = new MatchUpdater(new InMemoryMatchFinder());

  @Test
  void shouldThrowExceptionWhenCommandIsNull() {
    assertThrows(NullPointerException.class, () -> matchUpdater.cancelMatch(null));
  }

  @Test
  void shouldThrowExceptionWhenMatchIdIsNull() {
    CancelMatch cancelMatch = CancelMatch
        .builder()
        .matchId(null)
        .build();

    assertThrows(NullPointerException.class, () -> matchUpdater.cancelMatch(cancelMatch));
  }

  @Test
  void shouldThrowExceptionWhenMatchIsNotFound() {
    CancelMatch cancelMatch = CancelMatch
        .builder()
        .matchId(1L)
        .build();

    assertThrows(MatchNotFoundException.class, () -> matchUpdater.cancelMatch(cancelMatch));
  }


}