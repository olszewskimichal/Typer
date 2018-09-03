package pl.michal.olszewski.typer.match.dto.command;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import pl.michal.olszewski.typer.CommandValid;

@Getter
@AllArgsConstructor
@Builder
public class IntegrateMatchWithLivescore implements CommandValid {

  private final Long matchId;
  private final Long livescoreId;
  private final Long livescoreLeagueId;

  @Override
  public void validCommand() {
    if (matchId == null || livescoreId == null || livescoreLeagueId == null) {
      throw new IllegalArgumentException("Nieprawdłowe parametry do integracji z systemem zewnętrznym");
    }
  }
}
