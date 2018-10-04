package pl.michal.olszewski.typer.team.dto.command;

import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import pl.michal.olszewski.typer.CommandValid;

@AllArgsConstructor
@Builder
@Getter
@ToString
public class CreateNewTeam implements CommandValid {

  @NotNull
  private final String name;

  @Override
  public void validCommand() {
    if (name == null) {
      throw new IllegalArgumentException("Nie mozna stworzyc zespołu bez nazwy");
    }
  }
}
