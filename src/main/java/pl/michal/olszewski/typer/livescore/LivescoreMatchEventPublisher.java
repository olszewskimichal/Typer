package pl.michal.olszewski.typer.livescore;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;
import pl.michal.olszewski.typer.livescore.command.FinishLivescoreMatch;

@Component
@Slf4j
class LivescoreMatchEventPublisher {

  static final String FINISHED_MATCH_QUEUE = "finishedLivescoreMatchQueue";
  private final JmsTemplate jmsTemplate;

  @Autowired
  LivescoreMatchEventPublisher(JmsTemplate jmsTemplate) {
    this.jmsTemplate = jmsTemplate;
  }

  void sendMatchFinishedToJMS(FinishLivescoreMatch matchFinished) {
    log.info("Send {}", matchFinished);
    matchFinished.validCommand();
    jmsTemplate.convertAndSend(FINISHED_MATCH_QUEUE, matchFinished);
  }
}
