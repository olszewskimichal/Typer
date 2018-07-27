package pl.michal.olszewski.typer.bet;

import lombok.Value;

@Value
class BetChecked {

  private final long betId;
  private final long points;
}
