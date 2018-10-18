package pl.michal.olszewski.typer.match.domain;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.jms.core.JmsTemplate;
import pl.michal.olszewski.typer.match.dto.command.CheckMatchResults;

class CheckMatchPublisherTest {

  private JmsTemplate jmsTemplate;
  private CheckMatchPublisher eventPublisher;

  @BeforeEach
  void configureSystemUnderTests() {
    jmsTemplate = mock(JmsTemplate.class);
    eventPublisher = new CheckMatchPublisher(jmsTemplate);
  }

  @Test
  void shouldSendBetCheckedEventToQueue() {
    CheckMatchResults checkMatchResults = CheckMatchResults.builder().build();

    eventPublisher.sendCheckMatchCommandToJms(checkMatchResults);

    Mockito.verify(jmsTemplate, times(1)).convertAndSend(CheckMatchPublisher.CHECK_MATCH_COMMAND_QUEUE, checkMatchResults);
    Mockito.verifyNoMoreInteractions(jmsTemplate);
  }
}