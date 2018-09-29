package pl.michal.olszewski.typer.livescore;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;
import pl.michal.olszewski.typer.livescore.command.FinishLivescoreMatch;
import pl.michal.olszewski.typer.match.dto.command.CheckMatchResults;
import reactor.core.publisher.Mono;

@Slf4j
@Component
class CheckMatchResultListener {

  private final LivescoreApiClient livescoreApiClient;
  private final LivescoreMatchEventPublisher publisher;

  public CheckMatchResultListener(LivescoreApiClient livescoreApiClient, LivescoreMatchEventPublisher publisher) {
    this.livescoreApiClient = livescoreApiClient;
    this.publisher = publisher;
  }

  @JmsListener(destination = "checkMatchCommandQueue")
  void checkMatchCommandJMS(CheckMatchResults command) {
    log.info("Received {}", command);
    Mono<PastMatchesDTO> pastMatchesForLeagueFromDate = livescoreApiClient.getPastMatchesForLeagueFromDate(command.getLivescoreLeagueId(), command.getDate());
    pastMatchesForLeagueFromDate
        .map(PastMatchesDTO::getData)
        .flatMapIterable(PastMatchesDataDTO::getMatches)
        .map(v -> new FinishLivescoreMatch(v.getId(), v.getHomeGoals(), v.getAwayGoals()))
        .subscribe(publisher::sendMatchFinishedToJMS);
  }

}
