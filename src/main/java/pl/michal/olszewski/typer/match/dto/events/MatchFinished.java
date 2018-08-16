package pl.michal.olszewski.typer.match.dto.events;


import lombok.Builder;
import lombok.Getter;

@Getter
public class MatchFinished extends MatchEventBase {

  private final Long homeGoals;
  private final Long awayGoals;
  private final Long betPolicyId;

  @Builder
  public MatchFinished(Long matchId, Long homeGoals, Long awayGoals, Long betPolicyId) {
    super(matchId);
    this.homeGoals = homeGoals;
    this.awayGoals = awayGoals;
    this.betPolicyId = betPolicyId;
  }
}
