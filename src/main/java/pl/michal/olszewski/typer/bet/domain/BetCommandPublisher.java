package pl.michal.olszewski.typer.bet.domain;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;
import pl.michal.olszewski.typer.bet.dto.command.CancelBet;
import pl.michal.olszewski.typer.bet.dto.command.CheckBet;

@Component
@Slf4j
class BetCommandPublisher {

  static final String CANCEL_BET_COMMAND_QUEUE = "cancelBetCommandQueue";
  static final String CHECK_BET_COMMAND_QUEUE = "checkBetCommandQueue";
  private final JmsTemplate jmsTemplate;

  @Autowired
  BetCommandPublisher(JmsTemplate jmsTemplate) {
    this.jmsTemplate = jmsTemplate;
  }

  void sendCancelCommandToJms(CancelBet command) {
    log.info("Send {}", command);
    jmsTemplate.convertAndSend(CANCEL_BET_COMMAND_QUEUE, command);
  }

  void sendCheckCommandToJms(CheckBet command) {
    log.info("Send {}", command);
    jmsTemplate.convertAndSend(CHECK_BET_COMMAND_QUEUE, command);
  }
}
