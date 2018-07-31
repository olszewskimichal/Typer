package pl.michal.olszewski.typer.bet.domain;

import pl.michal.olszewski.typer.bet.dto.command.CheckBet;
import pl.michal.olszewski.typer.bet.dto.events.BetChecked;

class MyBetPolicy implements BetPolicy {

  private static final long POINTS_FOR_EXACTLY_THE_SAME_RESULT = 2L;
  private static final long POINTS_FOR_CORRECT_RESULT = 1L;
  private static final long POINTS_FOR_INCORRECT_RESULT = 0L;

  @Override
  public BetChecked calculatePoints(CheckBet checkBet) {
    checkBet.validCommand();
    if (isGoalsEqual(checkBet.getBetHomeGoals(), checkBet.getExpectedHomeGoals()) &&
        isGoalsEqual(checkBet.getBetAwayGoals(), checkBet.getExpectedAwayGoals())) {
      return new BetChecked(checkBet.getBetId(), POINTS_FOR_EXACTLY_THE_SAME_RESULT);
    }

    long expectedDifference = checkBet.getExpectedHomeGoals() - checkBet.getExpectedAwayGoals();
    long predictedDifference = checkBet.getBetHomeGoals() - checkBet.getBetAwayGoals();
    if (isTheSameDifference(expectedDifference, predictedDifference)) {
      return new BetChecked(checkBet.getBetId(), POINTS_FOR_CORRECT_RESULT);
    }
    return new BetChecked(checkBet.getBetId(), POINTS_FOR_INCORRECT_RESULT);
  }

  private boolean isTheSameDifference(Long expectedDifference, Long predictedDifference) {
    return expectedDifference.equals(predictedDifference);
  }
}


