package pl.michal.olszewski.typer.match.dto.command;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import pl.michal.olszewski.typer.CommandValid;

@AllArgsConstructor
@Builder
@Getter

public class CreateNewRound implements CommandValid {

  private final String name;

  @Override
  public void validCommand() {
    if (name == null) {
      throw new IllegalArgumentException("Nazwa kolejki nie może byc pusta");
    }
  }
}
