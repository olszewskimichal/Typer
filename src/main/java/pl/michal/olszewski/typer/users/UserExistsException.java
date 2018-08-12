package pl.michal.olszewski.typer.users;

public class UserExistsException extends RuntimeException {

  public UserExistsException(String message) {
    super(message);
  }
}
