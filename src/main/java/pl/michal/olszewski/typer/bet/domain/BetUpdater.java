package pl.michal.olszewski.typer.bet.domain;

import java.util.Objects;
import pl.michal.olszewski.typer.bet.dto.command.BlockBet;
import pl.michal.olszewski.typer.bet.dto.command.CancelBet;
import pl.michal.olszewski.typer.bet.dto.command.CheckBet;

class BetUpdater {

  private final BetFinder betFinder;

  BetUpdater(BetFinder betFinder) {
    this.betFinder = betFinder;
  }

  Bet cancelBet(CancelBet command) {
    Objects.requireNonNull(command);
    command.validCommand();
    Bet bet = betFinder.findOneOrThrow(command.getBetId());
    bet.cancelBet();
    return bet;
  }

  Bet checkBet(CheckBet command) {
    Objects.requireNonNull(command);
    command.validCommand();
    Bet bet = betFinder.findOneOrThrow(command.getBetId());
    bet.checkBet();
    return bet;
  }

  Bet blockBet(BlockBet command) {
    Objects.requireNonNull(command);
    command.validCommand();
    Bet bet = betFinder.findOneOrThrow(command.getBetId());
    bet.blockBet();
    return bet;
  }
}
