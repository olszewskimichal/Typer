package pl.michal.olszewski.typer.match.dto.events;

import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
abstract class MatchEventBase {

  @Getter
  private final Long matchId;
  private final Instant occurredAt;
}
