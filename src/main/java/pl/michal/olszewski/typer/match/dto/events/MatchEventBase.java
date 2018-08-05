package pl.michal.olszewski.typer.match.dto.events;

import java.time.Instant;
import lombok.AllArgsConstructor;

@AllArgsConstructor
abstract class MatchEventBase {

  private final Long matchId;
  private final Instant occurredAt;
}
