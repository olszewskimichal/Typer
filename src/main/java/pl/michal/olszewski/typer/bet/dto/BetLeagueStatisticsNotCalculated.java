package pl.michal.olszewski.typer.bet.dto;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class BetLeagueStatisticsNotCalculated extends RuntimeException {

  public BetLeagueStatisticsNotCalculated(String message) {
    super(message);
  }
}
