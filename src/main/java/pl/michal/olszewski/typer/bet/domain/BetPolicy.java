package pl.michal.olszewski.typer.bet.domain;

import pl.michal.olszewski.typer.bet.dto.command.CheckBet;
import pl.michal.olszewski.typer.bet.dto.events.BetChecked;

interface BetPolicy {

  BetChecked calculatePoints(CheckBet checkBetMatchEvent);

  default boolean isGoalsEqual(Long predictedGoals, Long expectedGoals) {
    return predictedGoals.equals(expectedGoals);
  }

}
