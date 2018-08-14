package pl.michal.olszewski.typer.users.domain;

import java.util.Objects;
import pl.michal.olszewski.typer.users.UserExistsException;
import pl.michal.olszewski.typer.users.dto.command.CreateNewUser;

class UserCreator {

  private final UserFinder userFinder;

  UserCreator(UserFinder userFinder) {
    this.userFinder = userFinder;
  }

  public User from(CreateNewUser command) {
    Objects.requireNonNull(command);
    command.validCommand();

    userFinder.findByEmail(command.getEmail())
        .ifPresent(v -> {
          throw new UserExistsException("Uzytkownik o emailu  istnieje");
        });

    return User.builder()
        .username(command.getUsername())
        .email(command.getEmail())
        .build();
  }
}
