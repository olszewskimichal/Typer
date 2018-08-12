package pl.michal.olszewski.typer.users.dto.command;

import lombok.Builder;

@Builder
public class CreateNewUser {

  private final String username;
  private final String email;

}
