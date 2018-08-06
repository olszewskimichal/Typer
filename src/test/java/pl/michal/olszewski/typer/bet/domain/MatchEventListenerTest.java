package pl.michal.olszewski.typer.bet.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import pl.michal.olszewski.typer.bet.dto.command.CancelBet;
import pl.michal.olszewski.typer.bet.dto.command.CheckBet;
import pl.michal.olszewski.typer.match.dto.command.CancelMatch;
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
    ((InMemoryBetFinder) betFinder).save(2L, Bet.builder().matchId(2L).build());
    ((InMemoryBetFinder) betFinder).save(3L, Bet.builder().matchId(3L).build());
    ((InMemoryBetFinder) betFinder).save(4L, Bet.builder().matchId(3L).build());

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
    ((InMemoryBetFinder) betFinder).save(2L, Bet.builder().matchId(2L).build());
    ((InMemoryBetFinder) betFinder).save(3L, Bet.builder().matchId(3L).build());
    ((InMemoryBetFinder) betFinder).save(4L, Bet.builder().matchId(3L).build());

    MatchFinished matchFinished = MatchFinished.builder().matchId(3L).build();
    //when
    int result = matchEventListener.handleMatchFinishedEventJMS(matchFinished);
    //then
    assertThat(result).isEqualTo(2);
    Mockito.verify(eventPublisher, times(2)).sendCheckCommandToJms(Mockito.any(CheckBet.class));
  }
}