package pl.michal.olszewski.typer.match.dto;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class IllegalMatchMemberException extends RuntimeException {

  public IllegalMatchMemberException(String message) {
    super(message);
  }
}
