package pl.michal.olszewski.typer.team.dto;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class TeamExistsException extends RuntimeException {

  public TeamExistsException(String message) {
    super(message);
  }
}
