package pl.michal.olszewski.typer.match.dto.events;


import java.time.Instant;
import lombok.Builder;

public class MatchFinished extends MatchEventBase {

  @Builder
  public MatchFinished(Long matchId, Instant occurredAt) {
    super(matchId, occurredAt);
  }
}
