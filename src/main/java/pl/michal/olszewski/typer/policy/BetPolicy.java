package pl.michal.olszewski.typer.policy;

import pl.michal.olszewski.typer.bet.BetChecked;
import pl.michal.olszewski.typer.bet.CheckBetMatchEvent;

interface BetPolicy {
    BetChecked applyPolicy(CheckBetMatchEvent checkBetMatchEvent);
}
