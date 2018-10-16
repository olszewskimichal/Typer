package pl.michal.olszewski.typer.match.dto.command;

import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import pl.michal.olszewski.typer.CommandValid;

@Getter
@AllArgsConstructor
@Builder
@ToString
public class IntegrateMatchWithLivescore implements CommandValid {

  @NotNull
  private final Long matchId;
  @NotNull
  private final Long livescoreId;
  @NotNull
  private final Long livescoreLeagueId;

  @Override
  public void validCommand() {
    if (matchId == null || livescoreId == null || livescoreLeagueId == null) {
      throw new IllegalArgumentException("Nieprawdłowe parametry do integracji z systemem zewnętrznym " + this.toString());
    }
  }
}
