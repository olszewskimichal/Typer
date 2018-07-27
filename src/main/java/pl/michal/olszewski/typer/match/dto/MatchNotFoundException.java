package pl.michal.olszewski.typer.match.dto;

public class MatchNotFoundException extends RuntimeException {

  public MatchNotFoundException(String message) {
    super(message);
  }

}
