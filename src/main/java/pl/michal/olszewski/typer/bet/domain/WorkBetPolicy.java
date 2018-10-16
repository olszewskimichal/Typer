package pl.michal.olszewski.typer.bet.domain;


import java.time.Instant;
import pl.michal.olszewski.typer.bet.dto.command.CheckBet;
import pl.michal.olszewski.typer.bet.dto.events.BetChecked;

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
  public BetChecked calculatePoints(CheckBet checkBet) {
    checkBet.validCommand();
    if (resultIsDraw(checkBet)
        && predictedWasDraw(checkBet)) {
      return checkResultWhenDraw(checkBet);
    }

    if (
        checkBet.getBetAwayGoals() > checkBet.getBetHomeGoals()
            && checkBet.getExpectedAwayGoals() > checkBet.getExpectedHomeGoals()) {
      return checkResultWhenIsNotDraw(checkBet);
    }

    if (checkBet.getBetAwayGoals() < checkBet.getBetHomeGoals()
        && checkBet.getExpectedAwayGoals() < checkBet.getExpectedHomeGoals()) {
      return checkResultWhenIsNotDraw(checkBet);
    }

    return new BetChecked(checkBet.getBetId(), Instant.now(), 0L);
  }

  private BetChecked checkResultWhenDraw(CheckBet checkBet) {
    if (isGoalsEqual(checkBet.getExpectedHomeGoals(), checkBet.getBetHomeGoals())) {
      return new BetChecked(checkBet.getBetId(), Instant.now(), 5L);
    }
    return new BetChecked(checkBet.getBetId(), Instant.now(), 3L);
  }

  private BetChecked checkResultWhenIsNotDraw(CheckBet checkBet) {
    if (isGoalsEqual(checkBet.getBetHomeGoals(), checkBet.getExpectedHomeGoals()) && isGoalsEqual(checkBet.getBetAwayGoals(), checkBet.getExpectedAwayGoals())) {
      return new BetChecked(checkBet.getBetId(), Instant.now(), 4L);
    }

    if (isGoalsEqual(checkBet.getBetAwayGoals(), checkBet.getExpectedAwayGoals())
        || isGoalsEqual(checkBet.getBetHomeGoals(), checkBet.getExpectedHomeGoals())) {
      return new BetChecked(checkBet.getBetId(), Instant.now(), 2L);
    }
    return new BetChecked(checkBet.getBetId(), Instant.now(), 1L);
  }

  private boolean resultIsDraw(CheckBet checkBet) {
    return checkBet.getExpectedAwayGoals().equals(checkBet.getExpectedHomeGoals());
  }

  private boolean predictedWasDraw(CheckBet checkBet) {
    return checkBet.getBetHomeGoals().equals(checkBet.getBetAwayGoals());
  }

}
