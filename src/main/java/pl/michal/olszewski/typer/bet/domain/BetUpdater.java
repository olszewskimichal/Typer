package pl.michal.olszewski.typer.bet.domain;

import pl.michal.olszewski.typer.bet.dto.command.CancelBet;
import pl.michal.olszewski.typer.bet.dto.command.CheckBet;

public class BetUpdater {

  private final BetFinder betFinder;

  public BetUpdater(BetFinder betFinder) {
    this.betFinder = betFinder;
  }

  public Bet cancelBet(CancelBet cancelBet) {
    return null;
  }

  public Bet finishBet(CheckBet finishBet) {
    return null;
  }
}
