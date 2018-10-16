package pl.michal.olszewski.typer.users.dto.dto;

import lombok.Value;

@Value
public class UserInfo {

  private final Long id;
  private final String email;
  private final String username;
}
