package pl.michal.olszewski.typer.users.dto.command;

import lombok.Builder;
import lombok.Getter;
import pl.michal.olszewski.typer.CommandValid;

@Builder
@Getter
public class CreateNewUser implements CommandValid {

  private final String username;
  private final String email;

  @Override
  public void validCommand() {
    if (username == null || email == null) {
      throw new IllegalArgumentException("Nie mozna stworzyc uzytkownika bez nazwy lub emailu");
    }
  }
}
