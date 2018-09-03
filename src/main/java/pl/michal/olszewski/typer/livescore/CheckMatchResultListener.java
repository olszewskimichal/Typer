package pl.michal.olszewski.typer.livescore;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import pl.michal.olszewski.typer.match.dto.command.CheckMatchResults;

@Slf4j
public class CheckMatchResultListener {

  private final LivescoreApiClient livescoreApiClient;

  public CheckMatchResultListener(LivescoreApiClient livescoreApiClient) {
    this.livescoreApiClient = livescoreApiClient;
  }

  @JmsListener(destination = "checkMatchCommandQueue")
  void handleCancelBetEventJMS(CheckMatchResults command) {
    log.info("Received {}", command);
    livescoreApiClient.getPastMatchesForLeagueFromDate(command.getLivescoreLeagueId(), command.getDate());
  }

}
