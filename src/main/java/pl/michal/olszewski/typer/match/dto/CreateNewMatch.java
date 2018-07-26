package pl.michal.olszewski.typer.match.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class CreateNewMatch {
  private final Long homeTeamId;
  private final Long awayTeamId;
}
