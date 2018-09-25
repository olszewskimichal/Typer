package pl.michal.olszewski.typer.bet.dto.read;

import lombok.Value;

@Value
public class BetResult {

  private final Long betId;
  private final Long matchId;
  private final Long userId;
  private final Long points;
  private final Long homeGoals;
  private final Long awayGoals;
}
