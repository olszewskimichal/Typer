package pl.michal.olszewski.typer.match.dto.events;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@Getter
@NoArgsConstructor
@ToString
abstract class MatchEventBase {

  private Long matchId;
}
