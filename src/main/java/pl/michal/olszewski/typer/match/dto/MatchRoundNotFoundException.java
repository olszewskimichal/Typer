package pl.michal.olszewski.typer.match.dto;

public class MatchRoundNotFoundException extends RuntimeException {

  public MatchRoundNotFoundException(String message) {
    super(message);
  }

}
