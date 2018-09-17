package pl.michal.olszewski.typer.team.dto;

public class TeamExistsException extends RuntimeException {

  public TeamExistsException(String message) {
    super(message);
  }
}
