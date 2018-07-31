package pl.michal.olszewski.typer.bet.domain;

class BetNotFoundException extends RuntimeException {

  BetNotFoundException(String msg) {
    super(msg);
  }
}
