package pl.michal.olszewski.typer.bet;

/**
 * Zasady punktacji:
 * •	Trafiając kto wygra bez względu na ilość strzelonych bramek otrzymuje się 1 pkt
 * •	Trafiając kto wygra oraz ilość strzelonych bramek przez jedną z drużyn dostajemy 2 pkt.
 * •	Trafiając remis bez względu na ilość strzelonych bramek otrzymujemy 3 pkt.
 * •	Trafiając idealnie wynik , kto wygrał bądź przegrał otrzymujemy 4 punkty.
 * •	Trafiając idealnie remis w tym ilość strzelonych bramek otrzymujemy 5 pkt
 */
class WorkBetPolicy implements BetPolicy {

  @Override
  public long calculatePoints(CheckBetMatchEvent betMatchEvent) {
    if (resultIsDraw(betMatchEvent)
        && predictedWasDraw(betMatchEvent)) {
      return checkResultWhenDraw(betMatchEvent);
    }

    if (
        betMatchEvent.getBetAwayGoals() > betMatchEvent.getBetHomeGoals()
            && betMatchEvent.getExpectedAwayGoals() > betMatchEvent.getExpectedHomeGoals()) {
      return checkResultWhenIsNotDraw(betMatchEvent);
    }

    if (betMatchEvent.getBetAwayGoals() < betMatchEvent.getBetHomeGoals()
        && betMatchEvent.getExpectedAwayGoals() < betMatchEvent.getExpectedHomeGoals()) {
      return checkResultWhenIsNotDraw(betMatchEvent);
    }

    return 0L;
  }

  private long checkResultWhenDraw(CheckBetMatchEvent betMatchEvent) {
    if (isGoalsEqual(betMatchEvent.getExpectedHomeGoals(), betMatchEvent.getBetHomeGoals())) {
      return 5L;
    }
    return 3L;
  }

  private long checkResultWhenIsNotDraw(CheckBetMatchEvent betMatchEvent) {
    if (betMatchEvent.getBetAwayGoals() - betMatchEvent.getBetHomeGoals() == betMatchEvent.getExpectedAwayGoals() - betMatchEvent.getExpectedHomeGoals()) {
      return 4L;
    }

    if (isGoalsEqual(betMatchEvent.getBetAwayGoals(), betMatchEvent.getExpectedAwayGoals())
        || isGoalsEqual(betMatchEvent.getBetHomeGoals(), betMatchEvent.getExpectedHomeGoals())) {
      return 2L;
    }
    return 1L;
  }

  private boolean resultIsDraw(CheckBetMatchEvent betMatchEvent) {
    return betMatchEvent.getExpectedAwayGoals() == betMatchEvent.getExpectedHomeGoals();
  }

  private boolean predictedWasDraw(CheckBetMatchEvent betMatchEvent) {
    return betMatchEvent.getBetHomeGoals() == betMatchEvent.getBetAwayGoals();
  }

}
