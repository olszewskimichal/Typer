package pl.michal.olszewski.typer.match.domain;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.scheduling.annotation.Scheduled;
import pl.michal.olszewski.typer.match.dto.command.CheckMatchResults;

class MatchUpdateScheduler {

  private final MatchFinder matchFinder;
  private final CheckMatchPublisher matchPublisher;

  MatchUpdateScheduler(MatchFinder matchFinder, CheckMatchPublisher matchPublisher) {
    this.matchFinder = matchFinder;
    this.matchPublisher = matchPublisher;
  }

  @Scheduled(fixedDelay = 30000)
  void createCheckMatchResultCommands() {
    List<Match> matchList = matchFinder.findByStatusForLivescoreUpdate(MatchStatus.NEW);
    List<Long> list = matchList
        .stream()
        .map(Match::getLivescoreId)
        .collect(Collectors.toList());
    if (list.isEmpty()) {
      return;
    }
    Instant date = matchList
        .stream()
        .map(Match::getStartDate)
        .min(Instant::compareTo)
        .orElseThrow(() -> new IllegalArgumentException("Nie może byc sytuacji że są mecze a nie mają daty rozpoczęcia"));
    CheckMatchResults checkMatchResults = new CheckMatchResults(date, list);
    matchPublisher.sendCheckMatchCommandToJms(checkMatchResults);
  }
}
