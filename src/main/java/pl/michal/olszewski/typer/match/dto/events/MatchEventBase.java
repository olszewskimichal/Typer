package pl.michal.olszewski.typer.match.dto.events;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Getter
@NoArgsConstructor
abstract class MatchEventBase {

  private Long matchId;
}
