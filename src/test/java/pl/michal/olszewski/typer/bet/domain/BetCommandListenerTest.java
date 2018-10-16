package pl.michal.olszewski.typer.bet.domain;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import pl.michal.olszewski.typer.bet.dto.command.CancelBet;
import pl.michal.olszewski.typer.bet.dto.command.CheckBet;

class BetCommandListenerTest {

  private BetCommandListener commandListener;
  private BetUpdater betUpdater;

  @BeforeEach
  void configureSystemUnderTests() {
    betUpdater = mock(BetUpdater.class);
    commandListener = new BetCommandListener(betUpdater);
  }

  @Test
  void shouldHandleCancelBetEventFromQueue() {
    //given
    CancelBet cancelBet = CancelBet.builder().build();

    //when
    commandListener.handleCancelBetEventJMS(cancelBet);

    //then
    Mockito.verify(betUpdater, times(1)).cancelBet(cancelBet);
    Mockito.verifyNoMoreInteractions(betUpdater);
  }

  @Test
  void shouldHandleCheckBetEventFromQueue() {
    //given
    CheckBet checkBet = CheckBet.builder().build();

    //when
    commandListener.handleCheckBetEventJMS(checkBet);

    //then
    Mockito.verify(betUpdater, times(1)).checkBet(checkBet);
    Mockito.verifyNoMoreInteractions(betUpdater);
  }

}