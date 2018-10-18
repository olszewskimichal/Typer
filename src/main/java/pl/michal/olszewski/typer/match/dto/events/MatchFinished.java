package pl.michal.olszewski.typer.match.dto.events;


import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString(callSuper = true)
public final class MatchFinished extends MatchEventBase {

  private Long homeGoals;
  private Long awayGoals;
  private Long betPolicyId;

  @Builder
  public MatchFinished(Long matchId, Long homeGoals, Long awayGoals, Long betPolicyId) {
    super(matchId);
    this.homeGoals = homeGoals;
    this.awayGoals = awayGoals;
    this.betPolicyId = betPolicyId;
  }


}
