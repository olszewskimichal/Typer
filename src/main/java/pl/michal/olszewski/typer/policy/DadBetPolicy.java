package pl.michal.olszewski.typer.policy;

import pl.michal.olszewski.typer.bet.BetChecked;
import pl.michal.olszewski.typer.bet.CheckBetMatchEvent;

public class DadBetPolicy implements BetPolicy {

  static long POINTS_FOR_CORRECT_RESULT = 1L;
  static long POINTS_FOR_INCORRECT_RESULT = 0L;

  @Override
  public BetChecked applyPolicy(CheckBetMatchEvent checkBetMatchEvent) {
    if (isGoalsEqual(checkBetMatchEvent.getBetAwayGoals(), checkBetMatchEvent.getExpectedAwayGoals()) && isGoalsEqual(checkBetMatchEvent.getBetHomeGoals(),
        checkBetMatchEvent.getExpectedHomeGoals())) {
      return new BetChecked(checkBetMatchEvent.getBetId(), POINTS_FOR_CORRECT_RESULT);
    }
    return new BetChecked(checkBetMatchEvent.getBetId(), POINTS_FOR_INCORRECT_RESULT);
  }


  private boolean isGoalsEqual(Long predictedGoals, Long expectedGoals) {
    return predictedGoals == expectedGoals;
  }
}
