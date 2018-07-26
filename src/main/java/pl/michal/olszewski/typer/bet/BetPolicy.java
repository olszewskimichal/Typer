package pl.michal.olszewski.typer.bet;

interface BetPolicy {

  long calculatePoints(CheckBetMatchEvent checkBetMatchEvent);

  default boolean isGoalsEqual(Long predictedGoals, Long expectedGoals) {
    return predictedGoals.equals(expectedGoals);
  }

}
