package pl.michal.olszewski.typer.bet.domain;


import org.springframework.stereotype.Component;
import pl.michal.olszewski.typer.bet.dto.read.BetLeagueUserStatistics;
import pl.michal.olszewski.typer.bet.dto.read.BetRoundUserStatistics;

@Component
class BetStatisticsService {

  private final BetFinder betFinder;

  BetStatisticsService(BetFinder betFinder) {
    this.betFinder = betFinder;
  }

  BetRoundUserStatistics getStatisticsForUserAndRound(Long userId, Long roundId) {
    return new BetRoundUserStatistics(userId, 1L, betFinder.findSumOfPointsForRoundAndUser(userId, roundId));
  }

  BetLeagueUserStatistics getStatisticsForUserAndLeague(Long userId, Long leagueId) {
    return new BetLeagueUserStatistics(userId, 1L, leagueId, betFinder.findSumOfPointsForLeagueAndUser(userId, leagueId));
  }

}
