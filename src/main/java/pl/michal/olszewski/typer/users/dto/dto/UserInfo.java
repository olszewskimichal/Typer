package pl.michal.olszewski.typer.users.dto.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UserInfo {

  private final Long id;
  private final String email;
  private final String username;
}
