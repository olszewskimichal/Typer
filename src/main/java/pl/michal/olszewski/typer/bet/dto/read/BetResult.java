package pl.michal.olszewski.typer.bet.dto.read;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class BetResult {

  private final Long betId;
  private final Long matchId;
  private final Long userId;
  private final Long points;
}
