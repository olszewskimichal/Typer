package pl.michal.olszewski.typer.policy;

import pl.michal.olszewski.typer.bet.BetChecked;
import pl.michal.olszewski.typer.bet.CheckBetMatchEvent;

public class DadBetPolicy implements BetPolicy {

  static final long POINTS_FOR_CORRECT_RESULT = 1L;
  static final long POINTS_FOR_INCORRECT_RESULT = 0L;

  @Override
  public BetChecked applyPolicy(CheckBetMatchEvent checkBet) {
    if (isGoalsEqual(checkBet.getBetAwayGoals(), checkBet.getExpectedAwayGoals()) && isGoalsEqual(checkBet.getBetHomeGoals(), checkBet.getExpectedHomeGoals())) {
      return new BetChecked(checkBet.getBetId(), POINTS_FOR_CORRECT_RESULT);
    }
    return new BetChecked(checkBet.getBetId(), POINTS_FOR_INCORRECT_RESULT);
  }
}
