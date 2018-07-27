package pl.michal.olszewski.typer.bet.dto;

import lombok.Value;

@Value
public class BetChecked {

  private final long betId;
  private final long points;
}
