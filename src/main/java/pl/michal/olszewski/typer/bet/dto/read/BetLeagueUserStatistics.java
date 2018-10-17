package pl.michal.olszewski.typer.bet.dto.read;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class BetLeagueUserStatistics {

  private final Long userId;
  private final Long position;
  private final Long leagueId;
  private final Long points;
}
