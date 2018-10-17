package pl.michal.olszewski.typer.match.dto.read;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class MatchRoundInfo {

  private final Long id;
  private final String name;
  private final Long leagueId;
}
