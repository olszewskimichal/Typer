package pl.michal.olszewski.typer.policy;

import pl.michal.olszewski.typer.bet.BetChecked;
import pl.michal.olszewski.typer.bet.CheckBetMatchEvent;

/**
 * Zasady punktacji:
 * •	Trafiając kto wygra bez względu na ilość strzelonych bramek otrzymuje się 1 pkt
 * •	Trafiając kto wygra oraz ilość strzelonych bramek przez jedną z drużyn dostajemy 2 pkt.
 * •	Trafiając remis bez względu na ilość strzelonych bramek otrzymujemy 3 pkt.
 * •	Trafiając idealnie wynik , kto wygrał bądź przegrał otrzymujemy 4 punkty.
 * •	Trafiając idealnie remis w tym ilość strzelonych bramek otrzymujemy 5 pkt
 */
public class WorkBetPolicy implements BetPolicy {

  @Override
  public BetChecked applyPolicy(CheckBetMatchEvent betMatchEvent) {
    if (resultIsDraw(betMatchEvent) && predictedWasDraw(betMatchEvent)) {
      return checkResultWhenDraw(betMatchEvent);
    }

    if (betMatchEvent.getBetAwayGoals() > betMatchEvent.getBetHomeGoals() && betMatchEvent.getExpectedAwayGoals() > betMatchEvent.getExpectedHomeGoals()) {
      return checkResultWhenIsNotDraw(betMatchEvent);
    }

    if (betMatchEvent.getBetAwayGoals() < betMatchEvent.getBetHomeGoals() && betMatchEvent.getExpectedAwayGoals() < betMatchEvent.getExpectedHomeGoals()) {
      return checkResultWhenIsNotDraw(betMatchEvent);
    }

    return new BetChecked(betMatchEvent.getBetId(), 0L);
  }

  private BetChecked checkResultWhenDraw(CheckBetMatchEvent betMatchEvent) {
    if (isGoalsEqual(betMatchEvent.getExpectedHomeGoals(), betMatchEvent.getBetHomeGoals())) {
      return new BetChecked(betMatchEvent.getBetId(), 5L);
    }
    return new BetChecked(betMatchEvent.getBetId(), 3L);
  }

  private BetChecked checkResultWhenIsNotDraw(CheckBetMatchEvent betMatchEvent) {
    if (betMatchEvent.getBetAwayGoals() - betMatchEvent.getBetHomeGoals() == betMatchEvent.getExpectedAwayGoals() - betMatchEvent.getExpectedHomeGoals()) {
      return new BetChecked(betMatchEvent.getBetId(), 4L);
    }

    if (isGoalsEqual(betMatchEvent.getBetAwayGoals(), betMatchEvent.getExpectedAwayGoals()) || isGoalsEqual(betMatchEvent.getBetHomeGoals(), betMatchEvent.getExpectedHomeGoals())) {
      return new BetChecked(betMatchEvent.getBetId(), 2L);
    }
    return new BetChecked(betMatchEvent.getBetId(), 1L);
  }

  private boolean resultIsDraw(CheckBetMatchEvent betMatchEvent) {
    return betMatchEvent.getExpectedAwayGoals() == betMatchEvent.getExpectedHomeGoals();
  }

  private boolean predictedWasDraw(CheckBetMatchEvent betMatchEvent) {
    return betMatchEvent.getBetHomeGoals() == betMatchEvent.getBetAwayGoals();
  }

}
