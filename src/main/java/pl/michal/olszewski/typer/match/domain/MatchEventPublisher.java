package pl.michal.olszewski.typer.match.domain;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;
import pl.michal.olszewski.typer.match.dto.events.MatchCanceled;
import pl.michal.olszewski.typer.match.dto.events.MatchFinished;

@Component
@Slf4j
class MatchEventPublisher {

  private final JmsTemplate jmsTemplate;

  @Autowired
  MatchEventPublisher(JmsTemplate jmsTemplate) {
    this.jmsTemplate = jmsTemplate;
  }

  void sendMatchCanceledToJMS(MatchCanceled matchCanceled) {
    log.info("Send {}", matchCanceled);
    jmsTemplate.convertAndSend("cancelMatchQueue", matchCanceled);
  }

  void sendMatchFinishedToJMS(MatchFinished matchFinished) {
    log.info("Send {}", matchFinished);
    jmsTemplate.convertAndSend("finishedMatchQueue", matchFinished);
  }
}
