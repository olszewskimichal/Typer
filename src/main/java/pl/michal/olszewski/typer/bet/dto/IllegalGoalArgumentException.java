package pl.michal.olszewski.typer.bet.dto;

public class IllegalGoalArgumentException extends RuntimeException {

  public IllegalGoalArgumentException(String message) {
    super(message);
  }
}
