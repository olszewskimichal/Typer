package pl.michal.olszewski.typer.bet.domain;

import lombok.extern.slf4j.Slf4j;
import org.springframework.integration.annotation.Publisher;
import org.springframework.stereotype.Component;
import pl.michal.olszewski.typer.bet.dto.events.BetChecked;

@Component
@Slf4j
class BetEventPublisher {

  @Publisher(channel = "betChecked")
  void sendBetCheckedToPayment(BetChecked event) {
    log.debug("Send {}", event);
  }
}
