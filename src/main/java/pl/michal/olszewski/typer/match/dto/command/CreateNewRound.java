package pl.michal.olszewski.typer.match.dto.command;

import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import pl.michal.olszewski.typer.CommandValid;
import pl.michal.olszewski.typer.match.dto.MatchLeagueNotFoundException;

@AllArgsConstructor
@Builder
@Getter
@ToString
public class CreateNewRound implements CommandValid {

  @NotNull
  private final String name;
  @NotNull
  private final Long leagueId;

  @Override
  public void validCommand() {
    if (name == null) {
      throw new IllegalArgumentException("Nazwa kolejki nie może byc pusta");
    }

    if (leagueId == null) {
      throw new MatchLeagueNotFoundException("Nieprawidłowy identyfikator ligi");
    }
  }
}
