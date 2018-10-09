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
import pl.michal.olszewski.typer.match.domain.MatchLeagueFinder;

@Component
@Profile("prod")
@EnableJpaAuditing
class BetStatisticsScheduler {

  private final BetFinder betFinder;
  private final BetStatisticsProperties statisticsProperties;
  private final BetStatisticsCalculator betStatisticsCalculator;
  private final MatchLeagueFinder matchLeagueFinder;

  BetStatisticsScheduler(BetFinder betFinder, BetStatisticsProperties statisticsProperties, BetStatisticsCalculator betStatisticsCalculator,
      MatchLeagueFinder matchLeagueFinder) {
    this.betFinder = betFinder;
    this.statisticsProperties = statisticsProperties;
    this.betStatisticsCalculator = betStatisticsCalculator;
    this.matchLeagueFinder = matchLeagueFinder;
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
    calculateStatsForRounds(modifiedAfter, now);
    calculateStatsForLeagues(now, roundForCalculation);
  }

  private void calculateStatsForLeagues(Instant now, Set<Long> roundForCalculation) {
    List<Long> leagueIdsForRounds = matchLeagueFinder.findLeagueIdsForRounds(roundForCalculation);
    for (Long leagueId : leagueIdsForRounds) {
      betStatisticsCalculator.calculateStatisticsForLeague(leagueId);
    }
    statisticsProperties.setLastLeagueStatisticCalculationDate(now);
  }

  private void calculateStatsForRounds(List<Bet> modifiedAfter, Instant now) {
    Set<Long> roundForCalculation = modifiedAfter.stream().map(Bet::getMatchRoundId).collect(Collectors.toSet());
    for (Long roundId : roundForCalculation) {
      betStatisticsCalculator.calculateStatisticsForRound(roundId);
    }
    statisticsProperties.setLastRoundStatisticCalculationDate(now);
  }
}
