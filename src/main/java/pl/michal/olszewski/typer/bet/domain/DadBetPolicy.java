package pl.michal.olszewski.typer.bet.domain;

import pl.michal.olszewski.typer.bet.dto.command.CheckBet;
import pl.michal.olszewski.typer.bet.dto.events.BetChecked;

class DadBetPolicy implements BetPolicy {

  private static final long POINTS_FOR_CORRECT_RESULT = 1L;
  private static final long POINTS_FOR_INCORRECT_RESULT = 0L;

  @Override
  public BetChecked calculatePoints(CheckBet checkBet) {
    checkBet.validCommand();
    if (isGoalsEqual(checkBet.getBetAwayGoals(), checkBet.getExpectedAwayGoals()) && isGoalsEqual(checkBet.getBetHomeGoals(), checkBet.getExpectedHomeGoals())) {
      return new BetChecked(checkBet.getBetId(), POINTS_FOR_CORRECT_RESULT);
    }
    return new BetChecked(checkBet.getBetId(), POINTS_FOR_INCORRECT_RESULT);
  }
}
