package pl.michal.olszewski.typer.users.domain;

import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import pl.michal.olszewski.typer.users.UserExistsException;
import pl.michal.olszewski.typer.users.dto.command.CreateNewUser;

@Component
@Slf4j
class UserCreator {

  private final UserFinder userFinder;

  UserCreator(UserFinder userFinder) {
    this.userFinder = userFinder;
  }

  public User from(CreateNewUser command) {
    log.debug("Creating user from command {}", command);
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
