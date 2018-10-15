package pl.michal.olszewski.typer.bet.domain;


import org.springframework.stereotype.Component;
import pl.michal.olszewski.typer.bet.dto.BetLeagueStatisticsNotCalculated;
import pl.michal.olszewski.typer.bet.dto.BetRoundStatisticsNotCalculated;
import pl.michal.olszewski.typer.bet.dto.read.BetLeagueUserStatistics;
import pl.michal.olszewski.typer.bet.dto.read.BetRoundUserStatistics;
import pl.michal.olszewski.typer.bet.dto.read.RoundPoints;

@Component
class BetStatisticsService {

  private final BetRoundStatisticsFinder betRoundStatisticsFinder;
  private final BetLeagueStatisticsFinder betLeagueStatisticsFinder;

  public BetStatisticsService(BetRoundStatisticsFinder betRoundStatisticsFinder, BetLeagueStatisticsFinder betLeagueStatisticsFinder) {
    this.betRoundStatisticsFinder = betRoundStatisticsFinder;
    this.betLeagueStatisticsFinder = betLeagueStatisticsFinder;
  }

  BetRoundUserStatistics getStatisticsForUserAndRound(Long userId, Long roundId) {
    BetRoundStatistics betRoundStatistics = betRoundStatisticsFinder.findByUserIdAndRoundId(userId, roundId)
        .orElseThrow(() -> new BetRoundStatisticsNotCalculated("Wyniki jeszcze nie zostały naliczone"));
    return new BetRoundUserStatistics(userId, betRoundStatistics.getPosition(), new RoundPoints(roundId, betRoundStatistics.getPoints()));
  }

  BetLeagueUserStatistics getStatisticsForUserAndLeague(Long userId, Long leagueId) {
    BetLeagueStatistics leagueStatistics = betLeagueStatisticsFinder.findByUserIdAndLeagueId(userId, leagueId)
        .orElseThrow(() -> new BetLeagueStatisticsNotCalculated("Wyniki jeszcze nie zostały naliczone"));

    return new BetLeagueUserStatistics(userId, leagueStatistics.getPosition(), leagueId, leagueStatistics.getPoints());
  }

}
