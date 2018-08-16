package pl.michal.olszewski.typer.bet.domain;

import java.util.Objects;
import org.springframework.stereotype.Service;
import pl.michal.olszewski.typer.bet.dto.command.BlockBet;
import pl.michal.olszewski.typer.bet.dto.command.CancelBet;
import pl.michal.olszewski.typer.bet.dto.command.CheckBet;
import pl.michal.olszewski.typer.bet.dto.events.BetChecked;

@Service
class BetUpdater {

  private final BetFinder betFinder;
  private final BetEventPublisher eventPublisher;

  BetUpdater(BetFinder betFinder, BetEventPublisher eventPublisher) {
    this.betFinder = betFinder;
    this.eventPublisher = eventPublisher;
  }

  Bet cancelBet(CancelBet command) {
    Objects.requireNonNull(command);
    command.validCommand();

    Bet bet = betFinder.findOneOrThrow(command.getBetId());
    bet.setStatusAsCanceled();
    return bet;
  }

  Bet checkBet(CheckBet command) {
    Objects.requireNonNull(command);
    command.validCommand();

    Bet bet = betFinder.findOneOrThrow(command.getBetId());
    BetChecked betChecked = BetTypePolicy.fromValue(command.getBetPolicyId()).getBetPolicy().calculatePoints(command);

    eventPublisher.sendBetCheckedToJMS(betChecked);
    bet.setStatusAsChecked();
    return bet;
  }

  Bet blockBet(BlockBet command) {
    Objects.requireNonNull(command);
    command.validCommand();

    Bet bet = betFinder.findOneOrThrow(command.getBetId());
    bet.setStatusAsBlocked();
    return bet;
  }
}
