package pl.michal.olszewski.typer.match.dto.events;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
abstract class MatchEventBase {

  private final Long matchId;
}
