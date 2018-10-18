package pl.michal.olszewski.typer.match.dto.events;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@ToString
abstract class MatchEventBase {

  private Long matchId;
}
