package pl.michal.olszewski.typer.match.domain;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;
import pl.michal.olszewski.typer.match.dto.command.CheckMatchResults;

@Component
@Slf4j
class CheckMatchPublisher {

  static final String CHECK_MATCH_COMMAND_QUEUE = "checkMatchCommandQueue";
  private final JmsTemplate jmsTemplate;

  @Autowired
  CheckMatchPublisher(JmsTemplate jmsTemplate) {
    this.jmsTemplate = jmsTemplate;
  }

  void sendCheckMatchCommandToJms(CheckMatchResults command) {
    log.info("Send {}", command);
    jmsTemplate.convertAndSend(CHECK_MATCH_COMMAND_QUEUE, command);
  }
}
