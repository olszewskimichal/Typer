package pl.michal.olszewski.typer.match.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import pl.michal.olszewski.typer.bet.dto.IllegalGoalArgumentException;
import pl.michal.olszewski.typer.match.dto.MatchNotFoundException;
import pl.michal.olszewski.typer.match.dto.command.CancelMatch;
import pl.michal.olszewski.typer.match.dto.command.FinishMatch;
import pl.michal.olszewski.typer.match.dto.events.MatchCanceled;
import pl.michal.olszewski.typer.match.dto.events.MatchFinished;

class MatchUpdaterTest {

  private MatchFinder matchFinder = new InMemoryMatchFinder();
  private MatchEventPublisher eventPublisher;
  private MatchUpdater matchUpdater;

  @BeforeEach
  void configureSystemUnderTests() {
    eventPublisher = mock(MatchEventPublisher.class);
    matchUpdater = new MatchUpdater(matchFinder, new InMemoryMatchSaver(), eventPublisher);
    givenMatch().deleteAll();
  }

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

    assertThrows(MatchNotFoundException.class, () -> matchUpdater.cancelMatch(cancelMatch));
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
    Match expected = givenMatch()
        .buildAndSave(2L, MatchStatus.CANCELED);

    CancelMatch cancelMatch = CancelMatch
        .builder()
        .matchId(2L)
        .build();

    Match match = matchUpdater.cancelMatch(cancelMatch);

    assertThat(match).isNotNull();
    assertThat(match).isEqualToComparingFieldByField(expected);
    Mockito.verify(eventPublisher, times(1)).sendMatchCanceledToJMS(Mockito.any(MatchCanceled.class));
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

    assertThrows(MatchNotFoundException.class, () -> matchUpdater.finishMatch(finishMatch));
  }

  @Test
  void shouldThrowExceptionWhenFinishedMatchIsNotFound() {
    FinishMatch finishMatch = FinishMatch
        .builder()
        .awayGoals(1L)
        .homeGoals(2L)
        .matchId(1L)
        .build();

    assertThrows(MatchNotFoundException.class, () -> matchUpdater.finishMatch(finishMatch));
  }

  @Test
  void shouldThrowExceptionWhenGoalsArgumentsAreNotSet() {
    FinishMatch finishMatchCmd1 = FinishMatch
        .builder()
        .awayGoals(1L)
        .matchId(1L)
        .build();

    FinishMatch finishMatchCmd2 = FinishMatch
        .builder()
        .homeGoals(1L)
        .matchId(1L)
        .build();

    assertThrows(IllegalGoalArgumentException.class, () -> matchUpdater.finishMatch(finishMatchCmd1));
    assertThrows(IllegalGoalArgumentException.class, () -> matchUpdater.finishMatch(finishMatchCmd2));

  }

  @Test
  void shouldFinishMatchWhenCommandIsValidAndMatchIsFound() {
    Match expected = givenMatch()
        .buildAndSave(3L, MatchStatus.FINISHED, 3L, 3L);

    FinishMatch finishMatch = FinishMatch
        .builder()
        .matchId(3L)
        .homeGoals(3L)
        .awayGoals(3L)
        .build();

    Match match = matchUpdater.finishMatch(finishMatch);

    assertThat(match).isNotNull();
    assertThat(match).isEqualToComparingFieldByField(expected);
    Mockito.verify(eventPublisher, times(1)).sendMatchFinishedToJMS(Mockito.any(MatchFinished.class));
  }

  private MatchFactory givenMatch() {
    return new MatchFactory(new InMemoryMatchSaver());
  }

}