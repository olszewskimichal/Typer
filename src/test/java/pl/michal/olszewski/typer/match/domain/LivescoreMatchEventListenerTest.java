package pl.michal.olszewski.typer.match.domain;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import pl.michal.olszewski.typer.livescore.dto.command.FinishLivescoreMatch;

class LivescoreMatchEventListenerTest {

  private LivescoreMatchEventListener livescoreMatchEventListener;
  private MatchUpdater matchUpdater;

  @BeforeEach
  void setUp() {
    matchUpdater = mock(MatchUpdater.class);
    livescoreMatchEventListener = new LivescoreMatchEventListener(matchUpdater);
  }

  @Test
  void shouldHandleCheckLivescoreMatchFinishedFromQueue() {

    FinishLivescoreMatch command = FinishLivescoreMatch.builder().build();
    //when
    livescoreMatchEventListener.checkLivescoreMatchFinishedJMS(command);
    //then
    Mockito.verify(matchUpdater, times(1)).finishLivescoreMatch(command);
    Mockito.verifyNoMoreInteractions(matchUpdater);
  }
}