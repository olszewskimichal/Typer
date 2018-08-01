package pl.michal.olszewski.typer.bet.domain;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;
import pl.michal.olszewski.typer.bet.dto.events.BetChecked;

@Component
@Slf4j
class BetEventPublisher {

  private final JmsTemplate jmsTemplate;

  @Autowired
  BetEventPublisher(JmsTemplate jmsTemplate) {
    this.jmsTemplate = jmsTemplate;
  }

  void sendBetCheckedToJMS(BetChecked betChecked) {
    log.info("Send {}", betChecked);
    jmsTemplate.convertAndSend("betCheckedQueue", betChecked);
  }
}
