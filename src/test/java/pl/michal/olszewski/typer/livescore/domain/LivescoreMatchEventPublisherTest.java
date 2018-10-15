package pl.michal.olszewski.typer.livescore.domain;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.jms.core.JmsTemplate;
import pl.michal.olszewski.typer.livescore.command.FinishLivescoreMatch;

class LivescoreMatchEventPublisherTest {

  private JmsTemplate jmsTemplate;
  private LivescoreMatchEventPublisher eventPublisher;

  @BeforeEach
  void configureSystemUnderTests() {
    jmsTemplate = mock(JmsTemplate.class);
    eventPublisher = new LivescoreMatchEventPublisher(jmsTemplate);
  }

  @Test
  void shouldSendBetCheckedEventToQueue() {
    FinishLivescoreMatch match = FinishLivescoreMatch.builder().livescoreMatchId(1L).awayGoals(1L).homeGoals(2L).build();

    eventPublisher.sendMatchFinishedToJMS(match);

    Mockito.verify(jmsTemplate, times(1)).convertAndSend(LivescoreMatchEventPublisher.FINISHED_MATCH_QUEUE, match);
    Mockito.verifyNoMoreInteractions(jmsTemplate);
  }

}