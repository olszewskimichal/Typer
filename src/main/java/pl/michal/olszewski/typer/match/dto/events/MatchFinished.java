package pl.michal.olszewski.typer.match.dto.events;


import lombok.Builder;
import lombok.Getter;

@Getter
public class MatchFinished extends MatchEventBase {

  private final Long homeGoals;
  private final Long awayGoals;

  @Builder
  public MatchFinished(Long matchId, Long homeGoals, Long awayGoals) {
    super(matchId);
    this.homeGoals = homeGoals;
    this.awayGoals = awayGoals;
  }
}
