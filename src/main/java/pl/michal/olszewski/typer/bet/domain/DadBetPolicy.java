package pl.michal.olszewski.typer.bet.domain;

import pl.michal.olszewski.typer.bet.dto.BetChecked;
import pl.michal.olszewski.typer.bet.dto.CheckBetMatchEvent;

class DadBetPolicy implements BetPolicy {

  private static final long POINTS_FOR_CORRECT_RESULT = 1L;
  private static final long POINTS_FOR_INCORRECT_RESULT = 0L;

  @Override
  public BetChecked calculatePoints(CheckBetMatchEvent checkBet) {
    if (isGoalsEqual(checkBet.getBetAwayGoals(), checkBet.getExpectedAwayGoals()) && isGoalsEqual(checkBet.getBetHomeGoals(), checkBet.getExpectedHomeGoals())) {
      return new BetChecked(checkBet.getBetId(), POINTS_FOR_CORRECT_RESULT);
    }
    return new BetChecked(checkBet.getBetId(), POINTS_FOR_INCORRECT_RESULT);
  }
}
