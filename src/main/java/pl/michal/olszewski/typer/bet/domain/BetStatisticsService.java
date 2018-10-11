package pl.michal.olszewski.typer.bet.domain;


import org.springframework.stereotype.Component;
import pl.michal.olszewski.typer.bet.dto.read.BetLeagueUserStatistics;
import pl.michal.olszewski.typer.bet.dto.read.BetRoundUserStatistics;
import pl.michal.olszewski.typer.bet.dto.read.RoundPoints;

@Component
class BetStatisticsService {

  private final BetStatisticsFinder betStatisticsFinder;

  BetStatisticsService(BetStatisticsFinder betStatisticsFinder) {
    this.betStatisticsFinder = betStatisticsFinder;
  }

  BetRoundUserStatistics getStatisticsForUserAndRound(Long userId, Long roundId) {
    BetRoundStatistics betRoundStatistics = betStatisticsFinder.findByUserIdAndRoundId(userId, roundId)
        .orElseThrow(() -> new IllegalArgumentException("Wyniki jeszcze nie zostały naliczone"));
    return new BetRoundUserStatistics(userId, betRoundStatistics.getPosition(), new RoundPoints(roundId, betRoundStatistics.getPoints()));
  }

  BetLeagueUserStatistics getStatisticsForUserAndLeague(Long userId, Long leagueId) {
    BetLeagueStatistics leagueStatistics = betStatisticsFinder.findByUserIdAndLeagueId(userId, leagueId)
        .orElseThrow(() -> new IllegalArgumentException("Wyniki jeszcze nie zostały naliczone"));
    return new BetLeagueUserStatistics(userId, leagueStatistics.getPosition(), leagueId, leagueStatistics.getPoints());
  }

}
