package pl.michal.olszewski.typer.policy;

import pl.michal.olszewski.typer.bet.BetChecked;
import pl.michal.olszewski.typer.bet.CheckBetMatchEvent;

public class MyBetPolicy implements BetPolicy {

  static final long POINTS_FOR_EXACTLY_THE_SAME_RESULT = 2L;
  static final long POINTS_FOR_CORRECT_RESULT = 1L;
  static final long POINTS_FOR_INCORRECT_RESULT = 0L;

  @Override
  public BetChecked applyPolicy(CheckBetMatchEvent checkBetMatchEvent) {
    if (isGoalsEqual(checkBetMatchEvent.getBetAwayGoals(), checkBetMatchEvent.getExpectedAwayGoals()) && isGoalsEqual(checkBetMatchEvent.getBetHomeGoals(),
        checkBetMatchEvent.getExpectedHomeGoals())) {
      return new BetChecked(checkBetMatchEvent.getBetId(), POINTS_FOR_EXACTLY_THE_SAME_RESULT);
    }
    if (isTheSameDifference(checkBetMatchEvent.getBetHomeGoals(), checkBetMatchEvent.getBetAwayGoals(), checkBetMatchEvent.getExpectedHomeGoals(), checkBetMatchEvent.getExpectedAwayGoals())) {
      return new BetChecked(checkBetMatchEvent.getBetId(), POINTS_FOR_CORRECT_RESULT);
    }
    return new BetChecked(checkBetMatchEvent.getBetId(), POINTS_FOR_INCORRECT_RESULT);
  }

  private boolean isTheSameDifference(Long homeGoals, Long awayGoals, Long expectedHomeGoals, Long expectedAwayGoals) {
    return (expectedHomeGoals - expectedAwayGoals) == (homeGoals - awayGoals);
  }
}


