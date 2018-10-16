package pl.michal.olszewski.typer.bet.dto.read;

import lombok.Value;

@Value
public class BetRoundUserStatistics {

  private final Long userId;
  private final Long position;
  private final RoundPoints roundPoints;
}
