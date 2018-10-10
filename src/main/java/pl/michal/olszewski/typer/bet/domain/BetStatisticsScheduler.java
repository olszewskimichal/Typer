package pl.michal.olszewski.typer.bet.domain;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Profile("prod")
@EnableJpaAuditing
class BetStatisticsScheduler {

  private final BetFinder betFinder;
  private final BetStatisticsProperties statisticsProperties;
  private final BetStatisticsCalculator betStatisticsCalculator;

  BetStatisticsScheduler(BetFinder betFinder, BetStatisticsProperties statisticsProperties, BetStatisticsCalculator betStatisticsCalculator) {
    this.betFinder = betFinder;
    this.statisticsProperties = statisticsProperties;
    this.betStatisticsCalculator = betStatisticsCalculator;
  }

  @Scheduled(fixedDelay = 50000)
  void calculateStatistics() {
    Instant lastCalcTime = Optional.ofNullable(statisticsProperties.getLastRoundStatisticCalculationDate()).orElse(Instant.MIN);
    List<Bet> modifiedAfter = betFinder.findByModifiedAfter(lastCalcTime);
    if (modifiedAfter.isEmpty()) {
      return;
    }
    Instant now = Instant.now();
    Set<Long> roundForCalculation = modifiedAfter.stream().map(Bet::getMatchRoundId).collect(Collectors.toSet());
    Set<Long> leagueForCalculation = modifiedAfter.stream().map(Bet::getLeagueId).collect(Collectors.toSet());
    calculateStatsForRounds(now, roundForCalculation);
    calculateStatsForLeagues(now, leagueForCalculation);
  }

  private void calculateStatsForLeagues(Instant now, Set<Long> leagueForCalculation) {
    for (Long leagueId : leagueForCalculation) {
      betStatisticsCalculator.calculateStatisticsForLeague(leagueId);
    }
    statisticsProperties.setLastLeagueStatisticCalculationDate(now);
  }

  private void calculateStatsForRounds(Instant now, Set<Long> roundForCalculation) {
    for (Long roundId : roundForCalculation) {
      betStatisticsCalculator.calculateStatisticsForRound(roundId);
    }
    statisticsProperties.setLastRoundStatisticCalculationDate(now);
  }
}
