package pl.michal.olszewski.typer.bet.dto.events;

import java.time.Instant;
import lombok.AllArgsConstructor;

@AllArgsConstructor
abstract class BetEventBase {

  private final Long betId;
  private final Instant occurredAt;
}
