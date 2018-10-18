package pl.michal.olszewski.typer.bet.dto.events;

import java.time.Instant;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString(callSuper = true)
@Getter
@NoArgsConstructor
public class BetChecked extends BetEventBase {

  private Long points;

  @Builder
  public BetChecked(Long betId, Instant occurredAt, Long points) {
    super(betId, occurredAt);
    this.points = points;
  }

}
