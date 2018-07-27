package pl.michal.olszewski.typer.bet.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class CheckBetMatchEvent {

  private final long policyId;
  private final long betId;
  private final long betAwayGoals;
  private final long betHomeGoals;
  private final long expectedAwayGoals;
  private final long expectedHomeGoals;

}
