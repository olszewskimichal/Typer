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

  @Override
  public void validCommand() {
    if (matchId == null || livescoreId == null) {
      throw new IllegalArgumentException("Nieprawdłowe parametry do integracji z systemem zewnętrznym");
    }
  }
}
