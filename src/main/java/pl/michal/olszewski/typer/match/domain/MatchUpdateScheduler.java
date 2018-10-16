package pl.michal.olszewski.typer.match.domain;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import pl.michal.olszewski.typer.match.dto.command.CheckMatchResults;

@Component
@Slf4j
class MatchUpdateScheduler {

  private final MatchFinder matchFinder;
  private final CheckMatchPublisher matchPublisher;

  MatchUpdateScheduler(MatchFinder matchFinder, CheckMatchPublisher matchPublisher) {
    this.matchFinder = matchFinder;
    this.matchPublisher = matchPublisher;
  }

  @Scheduled(fixedDelay = 300000)
  void createCheckMatchResultCommands() {
    log.debug("Rozpoczynam sprawdzanie wyników meczu - online");
    List<Match> matchList = matchFinder.findByStatusForLivescoreUpdate(MatchStatus.NEW);
    List<Long> list = matchList
        .stream()
        .map(Match::getLivescoreId)
        .collect(Collectors.toList());
    if (list.isEmpty()) {
      return;
    }
    log.debug("Sprawdzam dla nastepujacych livescoreId meczów {}", list);
    Map<Long, List<Match>> listMap = matchList.stream()
        .filter(v -> v.getLivescoreLeagueId() != null)
        .collect(Collectors.groupingBy(Match::getLivescoreLeagueId));
    for (Entry<Long, List<Match>> entry : listMap.entrySet()) {
      LocalDate date = entry.getValue()
          .stream()
          .filter(v -> v.getStartDate() != null)
          .map(Match::getStartDate)
          .map(v -> v.atZone(ZoneId.systemDefault()).toLocalDate())
          .min(LocalDate::compareTo)
          .orElse(LocalDate.now().minusWeeks(2));
      CheckMatchResults checkMatchResults = CheckMatchResults.builder().date(date).livescoreLeagueId(entry.getKey()).livescoreIds(list).build();
      matchPublisher.sendCheckMatchCommandToJms(checkMatchResults);
    }
  }
}
