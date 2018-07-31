package pl.michal.olszewski.typer.bet.domain;

import java.util.Objects;
import pl.michal.olszewski.typer.bet.dto.command.BlockBet;
import pl.michal.olszewski.typer.bet.dto.command.CancelBet;
import pl.michal.olszewski.typer.bet.dto.command.CheckBet;

public class BetUpdater {

  private final BetFinder betFinder;

  public BetUpdater(BetFinder betFinder) {
    this.betFinder = betFinder;
  }

  public Bet cancelBet(CancelBet command) {
    Objects.requireNonNull(command);
    Bet bet = betFinder.findOneOrThrow(command.getBetId());
    bet.cancelBet();
    return bet;
  }

  public Bet checkBet(CheckBet command) {
    Objects.requireNonNull(command);
    Bet bet = betFinder.findOneOrThrow(command.getBetId());
    bet.checkBet();
    return bet;
  }

  public Bet blockBet(BlockBet command) {
    return null;
  }
}
