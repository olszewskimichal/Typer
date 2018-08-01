package pl.michal.olszewski.typer.bet.dto.events;

import java.time.Instant;
import lombok.Builder;
import lombok.Getter;

public class BetChecked extends BetEventBase {

  @Getter
  private final Long points;

  @Builder
  public BetChecked(Long betId, Instant occurredAt, Long points) {
    super(betId, occurredAt);
    this.points = points;
  }
}
