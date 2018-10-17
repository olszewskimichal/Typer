package pl.michal.olszewski.typer.match.dto.read;

import lombok.AllArgsConstructor;
import lombok.Getter;
import pl.michal.olszewski.typer.match.domain.MatchStatus;

@AllArgsConstructor
@Getter
public class MatchInfo {

  private final Long id;
  private final Long homeGoals;
  private final Long awayGoals;
  private final Long homeTeamId;
  private final Long awayTeamId;
  private final MatchStatus status;
}
