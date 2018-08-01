package pl.michal.olszewski.typer.bet.domain;

import java.util.Objects;
import pl.michal.olszewski.typer.bet.dto.command.BlockBet;
import pl.michal.olszewski.typer.bet.dto.command.CancelBet;
import pl.michal.olszewski.typer.bet.dto.command.CheckBet;
import pl.michal.olszewski.typer.bet.dto.events.BetChecked;

class BetUpdater {

  private final BetFinder betFinder;
  private final BetEventPublisher eventPublisher;
  private final BetPolicy betPolicy;

  BetUpdater(BetFinder betFinder, BetEventPublisher eventPublisher, BetPolicy betPolicy) {
    this.betFinder = betFinder;
    this.eventPublisher = eventPublisher;
    this.betPolicy = betPolicy;
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
    BetChecked betChecked = betPolicy.calculatePoints(command);

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
