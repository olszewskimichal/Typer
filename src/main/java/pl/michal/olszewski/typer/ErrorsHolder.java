package pl.michal.olszewski.typer;

import java.util.List;

public class ErrorsHolder {

  private final List<Error> errors;

  ErrorsHolder(List<Error> errors) {
    this.errors = errors;
  }

  public List<Error> getErrors() {
    return errors;
  }
}