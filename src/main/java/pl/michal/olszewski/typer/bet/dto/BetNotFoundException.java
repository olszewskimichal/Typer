package pl.michal.olszewski.typer.bet.dto;

public class BetNotFoundException extends RuntimeException {

  public BetNotFoundException(String msg) {
    super(msg);
  }
}
