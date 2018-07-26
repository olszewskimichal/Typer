package pl.michal.olszewski.typer.bet;

class MyBetPolicy implements BetPolicy {

  private static final long POINTS_FOR_EXACTLY_THE_SAME_RESULT = 2L;
  private static final long POINTS_FOR_CORRECT_RESULT = 1L;
  private static final long POINTS_FOR_INCORRECT_RESULT = 0L;

  @Override
  public long calculatePoints(CheckBetMatchEvent checkBet) {
    if (isGoalsEqual(checkBet.getBetHomeGoals(), checkBet.getExpectedHomeGoals()) &&
        isGoalsEqual(checkBet.getBetAwayGoals(), checkBet.getExpectedAwayGoals())) {
      return POINTS_FOR_EXACTLY_THE_SAME_RESULT;
    }

    long expectedDifference = checkBet.getExpectedHomeGoals() - checkBet.getExpectedAwayGoals();
    long predictedDifference = checkBet.getBetHomeGoals() - checkBet.getBetAwayGoals();
    if (isTheSameDifference(expectedDifference, predictedDifference)) {
      return POINTS_FOR_CORRECT_RESULT;
    }
    return POINTS_FOR_INCORRECT_RESULT;
  }

  private boolean isTheSameDifference(Long expectedDifference, Long predictedDifference) {
    return expectedDifference.equals(predictedDifference);
  }
}


