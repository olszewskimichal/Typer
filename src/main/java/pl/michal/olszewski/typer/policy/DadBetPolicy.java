package pl.michal.olszewski.typer.policy;

import pl.michal.olszewski.typer.bet.BetChecked;
import pl.michal.olszewski.typer.bet.CheckBetMatchEvent;

public class DadBetPolicy implements BetPolicy {

  static long POINTS_FOR_CORRECT_RESULT = 1L;
  static long POINTS_FOR_INCORRECT_RESULT = 0L;

  @Override
  public BetChecked applyPolicy(CheckBetMatchEvent checkBetMatchEvent) {
    return null;
  }

}
