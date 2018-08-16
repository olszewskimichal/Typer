package pl.michal.olszewski.typer.bet.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;

import java.util.Arrays;
import java.util.Collections;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import pl.michal.olszewski.typer.bet.dto.command.CancelBet;
import pl.michal.olszewski.typer.bet.dto.command.CheckBet;
import pl.michal.olszewski.typer.match.dto.events.MatchCanceled;
import pl.michal.olszewski.typer.match.dto.events.MatchFinished;

class MatchEventListenerTest {

  private BetFinder betFinder = new InMemoryBetFinder();
  private BetCommandPublisher eventPublisher;

  private MatchEventListener matchEventListener;

  @BeforeEach
  void configureSystemUnderTests() {
    eventPublisher = mock(BetCommandPublisher.class);
    matchEventListener = new MatchEventListener(betFinder, eventPublisher);
  }

  @Test
  void shouldCreateAsManyCancelCommandAsManyBetForMatchExists() {
    //given
    givenBets()
        .buildNumberOfBetsForMatchAndSave(Arrays.asList(1L, 2L), 3L);
    givenBets()
        .buildNumberOfBetsForMatchAndSave(Collections.singletonList(3L), 2L);

    MatchCanceled matchCanceled = MatchCanceled.builder().matchId(3L).build();
    //when
    int result = matchEventListener.handleMatchCanceledEventJMS(matchCanceled);
    //then
    assertThat(result).isEqualTo(2);
    Mockito.verify(eventPublisher, times(2)).sendCancelCommandToJms(Mockito.any(CancelBet.class));
  }

  @Test
  void shouldCreateAsManyFinishCommandAsManyBetForMatchExists() {
    //given
    givenBets()
        .buildNumberOfBetsForMatchAndSave(Arrays.asList(1L, 2L), 3L);
    givenBets()
        .buildNumberOfBetsForMatchAndSave(Collections.singletonList(3L), 2L);

    MatchFinished matchFinished = MatchFinished.builder().matchId(3L).build();
    //when
    int result = matchEventListener.handleMatchFinishedEventJMS(matchFinished);
    //then
    assertThat(result).isEqualTo(2);
    Mockito.verify(eventPublisher, times(2)).sendCheckCommandToJms(Mockito.any(CheckBet.class));
  }

  private BetFactory givenBets() {
    return new BetFactory(betFinder);
  }

}