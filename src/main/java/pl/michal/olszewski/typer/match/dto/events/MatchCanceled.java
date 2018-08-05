package pl.michal.olszewski.typer.match.dto.events;

import java.time.Instant;
import lombok.Builder;

public final class MatchCanceled extends MatchEventBase {

  @Builder
  public MatchCanceled(Long matchId, Instant occurredAt) {
    super(matchId, occurredAt);
  }
}
