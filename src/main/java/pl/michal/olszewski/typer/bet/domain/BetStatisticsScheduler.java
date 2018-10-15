package pl.michal.olszewski.typer.bet.domain;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Profile("prod")
@EnableJpaAuditing
@Slf4j
class BetStatisticsScheduler {

  private final BetFinder betFinder;
  private final BetStatisticsProperties statisticsProperties;
  private final BetStatisticsCalculator betStatisticsCalculator;

  BetStatisticsScheduler(BetFinder betFinder, BetStatisticsProperties statisticsProperties, BetStatisticsCalculator betStatisticsCalculator) {
    this.betFinder = betFinder;
    this.statisticsProperties = statisticsProperties;
    this.betStatisticsCalculator = betStatisticsCalculator;
  }

  @Scheduled(fixedDelay = 500000)
  void calculateStatistics() {
    Instant lastCalcTime = Optional.ofNullable(statisticsProperties.getLastStatisticCalculationDate()).orElse(Instant.now().minus(100, ChronoUnit.DAYS));
    log.debug("Rozpoczynam naliczanie statystyk od daty {}", lastCalcTime);
    List<Bet> modifiedAfter = betFinder.findByModifiedAfter(lastCalcTime);
    if (modifiedAfter.isEmpty()) {
      return;
    }
    Instant now = Instant.now();
    Set<Long> roundForCalculation = modifiedAfter.stream().map(Bet::getMatchRoundId).collect(Collectors.toSet());
    Set<Long> leagueForCalculation = modifiedAfter.stream().map(Bet::getLeagueId).collect(Collectors.toSet());
    calculateStatsForRounds(roundForCalculation);
    calculateStatsForLeagues(leagueForCalculation);

    statisticsProperties.setLastStatisticCalculationDate(now);
  }

  private void calculateStatsForLeagues(Set<Long> leagueForCalculation) {
    log.trace("Naliczanie statystyk dla lig {}", leagueForCalculation);
    for (Long leagueId : leagueForCalculation) {
      betStatisticsCalculator.calculateStatisticsForLeague(leagueId);
    }
  }

  private void calculateStatsForRounds(Set<Long> roundForCalculation) {
    log.trace("Naliczanie statystyk dla rund {}", roundForCalculation);
    for (Long roundId : roundForCalculation) {
      betStatisticsCalculator.calculateStatisticsForRound(roundId);
    }
  }
}
