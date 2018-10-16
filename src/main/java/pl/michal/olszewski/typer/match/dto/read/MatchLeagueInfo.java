package pl.michal.olszewski.typer.match.dto.read;

import lombok.Value;

@Value
public class MatchLeagueInfo {

  private final Long id;
  private final String name;
  private final Long betTypePolicy;
}
