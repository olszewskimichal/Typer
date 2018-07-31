package pl.michal.olszewski.typer.bet.domain;

public class BetNotFoundException extends RuntimeException {

  public BetNotFoundException(String msg) {
    super(msg);
  }
}
