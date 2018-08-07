package pl.michal.olszewski.typer.bet.domain;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;
import pl.michal.olszewski.typer.bet.dto.command.CancelBet;
import pl.michal.olszewski.typer.bet.dto.command.CheckBet;

@Slf4j
@Component
class BetCommandListener {

  private final BetUpdater betUpdater;

  BetCommandListener(BetUpdater betUpdater) {
    this.betUpdater = betUpdater;
  }


  @JmsListener(destination = BetCommandPublisher.CANCEL_BET_COMMAND_QUEUE)
  void handleCancelBetEventJMS(CancelBet command) {
    log.info("Received {}", command);
    betUpdater.cancelBet(command);
  }

  @JmsListener(destination = BetCommandPublisher.CHECK_BET_COMMAND_QUEUE)
  void handleMatchFinishedEventJMS(CheckBet command) {
    log.info("Received {}", command);
    betUpdater.checkBet(command);
  }
}
