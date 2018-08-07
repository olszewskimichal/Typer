package pl.michal.olszewski.typer.bet.domain;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.jms.core.JmsTemplate;
import pl.michal.olszewski.typer.bet.dto.command.CancelBet;
import pl.michal.olszewski.typer.bet.dto.command.CheckBet;

class BetCommandPublisherTest {

  private JmsTemplate jmsTemplate;
  private BetCommandPublisher eventPublisher;

  @BeforeEach
  void configureSystemUnderTests() {
    jmsTemplate = mock(JmsTemplate.class);
    eventPublisher = new BetCommandPublisher(jmsTemplate);
  }

  @Test
  void shouldSendCancelCommandToQueue() {
    CancelBet cancelBet = CancelBet.builder().build();

    eventPublisher.sendCancelCommandToJms(cancelBet);

    Mockito.verify(jmsTemplate, times(1)).convertAndSend(BetCommandPublisher.CANCEL_BET_COMMAND_QUEUE, cancelBet);
    Mockito.verifyNoMoreInteractions(jmsTemplate);
  }

  @Test
  void shouldSendCheckCommandToQueue() {
    CheckBet checkBet = CheckBet.builder().build();

    eventPublisher.sendCheckCommandToJms(checkBet);

    Mockito.verify(jmsTemplate, times(1)).convertAndSend(BetCommandPublisher.CHECK_BET_COMMAND_QUEUE, checkBet);
    Mockito.verifyNoMoreInteractions(jmsTemplate);
  }
}