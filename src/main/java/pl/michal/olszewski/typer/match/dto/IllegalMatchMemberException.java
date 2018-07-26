package pl.michal.olszewski.typer.match.dto;

public class IllegalMatchMemberException extends RuntimeException {

  IllegalMatchMemberException(String message) {
    super(message);
  }
}
