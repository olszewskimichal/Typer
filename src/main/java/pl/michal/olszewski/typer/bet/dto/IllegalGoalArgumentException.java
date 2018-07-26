package pl.michal.olszewski.typer.bet.dto;

public class IllegalGoalArgumentException extends RuntimeException {

  IllegalGoalArgumentException(String message) {
    super(message);
  }
}
