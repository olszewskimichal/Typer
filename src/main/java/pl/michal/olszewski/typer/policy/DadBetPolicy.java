package pl.michal.olszewski.typer.policy;

import pl.michal.olszewski.typer.bet.BetChecked;
import pl.michal.olszewski.typer.bet.CheckBetMatchEvent;

public class DadBetPolicy implements BetPolicy {

  static final long POINTS_FOR_CORRECT_RESULT = 1L;
  static final long POINTS_FOR_INCORRECT_RESULT = 0L;

  @Override
  public BetChecked applyPolicy(CheckBetMatchEvent checkBetMatchEvent) {
    if (checkHomeGoals(checkBetMatchEvent) && checkAwayGoals(checkBetMatchEvent)) {
      return new BetChecked(checkBetMatchEvent.getBetId(), POINTS_FOR_CORRECT_RESULT);
    }
    return new BetChecked(checkBetMatchEvent.getBetId(), POINTS_FOR_INCORRECT_RESULT);
  }

  private boolean checkHomeGoals(CheckBetMatchEvent checkBetMatchEvent) {
    return isGoalsEqual(checkBetMatchEvent.getBetHomeGoals(), checkBetMatchEvent.getExpectedHomeGoals());
  }

  private boolean checkAwayGoals(CheckBetMatchEvent checkBetMatchEvent) {
    return isGoalsEqual(checkBetMatchEvent.getBetAwayGoals(), checkBetMatchEvent.getExpectedAwayGoals());
  }

}
