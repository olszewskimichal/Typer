package pl.michal.olszewski.typer.match.dto.command;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class CancelMatch {

  private Long matchId;

}
