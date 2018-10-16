package pl.michal.olszewski.typer.match.dto.command;

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
public class CreateNewLeague implements CommandValid {

  @NotNull
  private final Long betTypePolicy;
  @NotNull
  private final String name;

  @Override
  public void validCommand() {
    if (name == null) {
      throw new IllegalArgumentException("Nazwa kolejki nie może byc pusta");
    }
    if (betTypePolicy == null) {
      throw new IllegalArgumentException("Trzeba określić sposób naliczania zakladów");
    }
  }
}
