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

  static final String CANCEL_MATCH_QUEUE = "cancelMatchQueue";
  static final String FINISHED_MATCH_QUEUE = "finishedMatchQueue";
  private final JmsTemplate jmsTemplate;

  @Autowired
  MatchEventPublisher(JmsTemplate jmsTemplate) {
    this.jmsTemplate = jmsTemplate;
  }

  void sendMatchCanceledToJMS(MatchCanceled matchCanceled) {
    log.info("Send {}", matchCanceled);
    jmsTemplate.convertAndSend(CANCEL_MATCH_QUEUE, matchCanceled);
  }

  void sendMatchFinishedToJMS(MatchFinished matchFinished) {
    log.info("Send {}", matchFinished);
    jmsTemplate.convertAndSend(FINISHED_MATCH_QUEUE, matchFinished);
  }
}
