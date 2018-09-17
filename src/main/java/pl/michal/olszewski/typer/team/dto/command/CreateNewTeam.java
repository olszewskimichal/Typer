package pl.michal.olszewski.typer.team.dto.command;

import lombok.Builder;
import lombok.Getter;
import pl.michal.olszewski.typer.CommandValid;

@Builder
@Getter
public class CreateNewTeam implements CommandValid {

  private final String name;

  @Override
  public void validCommand() {
    if (name == null) {
      throw new IllegalArgumentException("Nie mozna stworzyc zespołu bez nazwy");
    }
  }
}
