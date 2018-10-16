package pl.michal.olszewski.typer.users.domain;

import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import pl.michal.olszewski.typer.users.UserExistsException;
import pl.michal.olszewski.typer.users.dto.command.CreateNewUser;

@Slf4j
class UserCreator {

  static User from(CreateNewUser command, UserFinder userFinder) {
    log.debug("Creating user from command {}", command);
    Objects.requireNonNull(command);
    command.validCommand();

    userFinder.findByEmail(command.getEmail())
        .ifPresent(v -> {
          throw new UserExistsException(String.format("Uzytkownik o emailu %s istnieje", command.getEmail()));
        });

    return User.builder()
        .username(command.getUsername())
        .email(command.getEmail())
        .build();
  }
}
