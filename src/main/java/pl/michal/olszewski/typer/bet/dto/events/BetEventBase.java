package pl.michal.olszewski.typer.bet.dto.events;

import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@ToString
@Getter
abstract class BetEventBase {

  private Long betId;
  private Instant occurredAt;

}
