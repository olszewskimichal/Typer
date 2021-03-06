package pl.michal.olszewski.typer.match.dto.command;

import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import pl.michal.olszewski.typer.CommandValid;
import pl.michal.olszewski.typer.match.dto.MatchNotFoundException;

@AllArgsConstructor
@Getter
@Builder
public class CancelMatch implements CommandValid {

  @NotNull
  private Long matchId;

  @Override
  public void validCommand() {
    if (matchId == null) {
      throw new MatchNotFoundException("Nie podano jaki mecz zostal obstawiony");
    }
  }
}
