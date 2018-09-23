package pl.michal.olszewski.typer.bet.dto.events;

import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@ToString
@NoArgsConstructor
@Getter
@Setter
abstract class BetEventBase {

  private Long betId;
  private Instant occurredAt;

}
