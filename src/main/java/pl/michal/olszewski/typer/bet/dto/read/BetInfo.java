package pl.michal.olszewski.typer.bet.dto.read;

import lombok.AllArgsConstructor;
import lombok.Getter;
import pl.michal.olszewski.typer.bet.domain.BetStatus;

@AllArgsConstructor
@Getter
public class BetInfo {

  private final Long id;
  private final Long userId;
  private final Long matchId;
  private final Long homeGoals;
  private final Long awayGoals;
  private final Long points;
  private final BetStatus status;
}
