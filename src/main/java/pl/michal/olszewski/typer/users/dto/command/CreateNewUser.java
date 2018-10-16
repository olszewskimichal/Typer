package pl.michal.olszewski.typer.users.dto.command;

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
public class CreateNewUser implements CommandValid {

  @NotNull
  private final String username;
  @NotNull
  private final String email;

  @Override
  public void validCommand() {
    if (username == null || email == null) {
      throw new IllegalArgumentException("Nie mozna stworzyc uzytkownika bez nazwy lub emailu");
    }
  }
}
