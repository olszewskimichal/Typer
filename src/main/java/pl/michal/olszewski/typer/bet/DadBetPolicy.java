package pl.michal.olszewski.typer.bet;

class DadBetPolicy implements BetPolicy {

  private static final long POINTS_FOR_CORRECT_RESULT = 1L;
  private static final long POINTS_FOR_INCORRECT_RESULT = 0L;

  @Override
  public long calculatePoints(CheckBetMatchEvent checkBet) {
    if (isGoalsEqual(checkBet.getBetAwayGoals(), checkBet.getExpectedAwayGoals()) && isGoalsEqual(checkBet.getBetHomeGoals(), checkBet.getExpectedHomeGoals())) {
      return POINTS_FOR_CORRECT_RESULT;
    }
    return POINTS_FOR_INCORRECT_RESULT;
  }
}
