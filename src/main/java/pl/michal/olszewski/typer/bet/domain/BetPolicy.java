package pl.michal.olszewski.typer.bet.domain;

import pl.michal.olszewski.typer.bet.dto.BetChecked;
import pl.michal.olszewski.typer.bet.dto.CheckBetMatchEvent;

interface BetPolicy {

  BetChecked calculatePoints(CheckBetMatchEvent checkBetMatchEvent);

  default boolean isGoalsEqual(Long predictedGoals, Long expectedGoals) {
    return predictedGoals.equals(expectedGoals);
  }

}
