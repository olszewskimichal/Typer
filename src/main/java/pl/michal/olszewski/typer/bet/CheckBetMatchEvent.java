package pl.michal.olszewski.typer.bet;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.Value;

@Value
@Builder
class CheckBetMatchEvent {

  private final long policyId;
  @Getter(AccessLevel.NONE)
  @Setter(AccessLevel.NONE)
  private final long betId;
  private final long betAwayGoals;
  private final long betHomeGoals;
  private final long expectedAwayGoals;
  private final long expectedHomeGoals;

  BetChecked checkPrediction(BetPolicy betPolicy) {
    return new BetChecked(betId, betPolicy.calculatePoints(this));
  }

}
