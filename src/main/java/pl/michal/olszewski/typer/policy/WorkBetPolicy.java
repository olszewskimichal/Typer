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
  public BetChecked applyPolicy(CheckBetMatchEvent checkBetMatchEvent) {
    return null;
  }
}
