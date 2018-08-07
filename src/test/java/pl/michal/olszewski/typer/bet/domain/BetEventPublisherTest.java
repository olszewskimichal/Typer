package pl.michal.olszewski.typer.bet.domain;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.jms.core.JmsTemplate;
import pl.michal.olszewski.typer.bet.dto.events.BetChecked;

class BetEventPublisherTest {

  private JmsTemplate jmsTemplate;
  private BetEventPublisher eventPublisher;

  @BeforeEach
  void configureSystemUnderTests() {
    jmsTemplate = mock(JmsTemplate.class);
    eventPublisher = new BetEventPublisher(jmsTemplate);
  }

  @Test
  void shouldSendBetCheckedEventToQueue() {
    BetChecked betChecked = BetChecked.builder().build();

    eventPublisher.sendBetCheckedToJMS(betChecked);

    Mockito.verify(jmsTemplate, times(1)).convertAndSend(BetEventPublisher.BET_CHECKED_QUEUE, betChecked);
    Mockito.verifyNoMoreInteractions(jmsTemplate);
  }
}