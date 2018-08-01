package pl.michal.olszewski.typer.bet.domain;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;
import pl.michal.olszewski.typer.bet.dto.events.BetChecked;

@Slf4j
@Component
public class BetEventListener {

  @JmsListener(destination = "betCheckedQueue")
  public void handleBetCheckedEventJMS(BetChecked event) {
    log.info("Received {}", event);
  }
}
