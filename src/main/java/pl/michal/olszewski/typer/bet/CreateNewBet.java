package pl.michal.olszewski.typer.bet;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
class CreateNewBet {

  private Long betHomeGoals;
  private Long betAwayGoals;
  private Long matchId;
}
