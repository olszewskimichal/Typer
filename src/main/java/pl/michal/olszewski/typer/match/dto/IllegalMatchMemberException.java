package pl.michal.olszewski.typer.match.dto;

public class IllegalMatchMemberException extends RuntimeException {

  public IllegalMatchMemberException(String message) {
    super(message);
  }
}
