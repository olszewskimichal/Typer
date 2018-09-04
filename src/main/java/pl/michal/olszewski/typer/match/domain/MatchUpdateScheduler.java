package pl.michal.olszewski.typer.match.domain;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
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
    Map<Long, List<Match>> listMap = matchList.stream()
        .filter(v -> v.getLivescoreLeagueId() != null)
        .collect(Collectors.groupingBy(Match::getLivescoreLeagueId));
    for (Entry<Long, List<Match>> entry : listMap.entrySet()) {
      LocalDate date = entry.getValue()
          .stream()
          .map(Match::getStartDate)
          .map(v -> v.atZone(ZoneId.systemDefault()).toLocalDate())
          .min(LocalDate::compareTo)
          .orElseThrow(() -> new IllegalArgumentException("Nie może byc sytuacji że są mecze a nie mają daty rozpoczęcia"));
      CheckMatchResults checkMatchResults = new CheckMatchResults(date, entry.getKey(), list);
      matchPublisher.sendCheckMatchCommandToJms(checkMatchResults);
    }
  }
}
