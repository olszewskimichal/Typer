package pl.michal.olszewski.typer.match.domain;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.jms.core.JmsTemplate;
import pl.michal.olszewski.typer.match.dto.events.MatchCanceled;
import pl.michal.olszewski.typer.match.dto.events.MatchFinished;

class MatchEventPublisherTest {

  private JmsTemplate jmsTemplate;
  private MatchEventPublisher eventPublisher;

  @BeforeEach
  void configureSystemUnderTests() {
    jmsTemplate = mock(JmsTemplate.class);
    eventPublisher = new MatchEventPublisher(jmsTemplate);
  }

  @Test
  void shouldSendMatchCanceledEventToQueue() {
    MatchCanceled matchCanceled = MatchCanceled.builder().build();

    eventPublisher.sendMatchCanceledToJMS(matchCanceled);

    Mockito.verify(jmsTemplate, times(1)).convertAndSend(MatchEventPublisher.CANCEL_MATCH_QUEUE, matchCanceled);
    Mockito.verifyNoMoreInteractions(jmsTemplate);
  }

  @Test
  void shouldSendMatchFinishedEventToQueue() {
    MatchFinished matchFinished = MatchFinished.builder().build();

    eventPublisher.sendMatchFinishedToJMS(matchFinished);

    Mockito.verify(jmsTemplate, times(1)).convertAndSend(MatchEventPublisher.FINISHED_MATCH_QUEUE, matchFinished);
    Mockito.verifyNoMoreInteractions(jmsTemplate);

  }
}