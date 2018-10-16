package pl.michal.olszewski.typer.match.dto;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class MatchLeagueNotFoundException extends RuntimeException {

  public MatchLeagueNotFoundException(String message) {
    super(message);
  }

}
