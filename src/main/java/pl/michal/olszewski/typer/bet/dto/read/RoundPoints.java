package pl.michal.olszewski.typer.bet.dto.read;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class RoundPoints {

  private final Long roundId;
  private final Long points;
}
