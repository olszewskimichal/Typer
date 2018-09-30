package pl.michal.olszewski.typer.bet.dto;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class BetNotCheckedException extends RuntimeException {

  public BetNotCheckedException(String msg) {
    super(msg);
  }
}
