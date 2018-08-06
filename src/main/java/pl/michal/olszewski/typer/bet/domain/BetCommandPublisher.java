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

  private final JmsTemplate jmsTemplate;

  @Autowired
  BetCommandPublisher(JmsTemplate jmsTemplate) {
    this.jmsTemplate = jmsTemplate;
  }

  void sendCancelCommandToJms(CancelBet command) {

  }

  void sendCheckCommandToJms(CheckBet command) {

  }
}
