package pl.michal.olszewski.typer.bet.dto;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class BetRoundStatisticsNotCalculated extends RuntimeException {

  public BetRoundStatisticsNotCalculated(String message) {
    super(message);
  }
}
