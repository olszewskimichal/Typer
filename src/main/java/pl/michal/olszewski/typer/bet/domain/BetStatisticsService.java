package pl.michal.olszewski.typer.bet.domain;


import java.util.List;
import java.util.stream.Collectors;
import org.springframework.data.domain.PageRequest;
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

  List<BetLeagueUserStatistics> getLeagueTOP(Long leagueId, Long pageSize) {
    return betLeagueStatisticsFinder.findByLeagueIdOrderByPointsDesc(leagueId, PageRequest.of(0, pageSize.intValue()))
        .getContent().stream()
        .map(v -> new BetLeagueUserStatistics(v.getUserId(), v.getPosition(), v.getLeagueId(), v.getPoints()))
        .collect(Collectors.toList());
  }

  List<BetRoundUserStatistics> getRoundTOP(Long roundId, Long pageSize) {
    return betRoundStatisticsFinder.findByRoundIdOrderByPointsDesc(roundId, PageRequest.of(0, pageSize.intValue()))
        .getContent().stream()
        .map(v -> new BetRoundUserStatistics(v.getUserId(), v.getPosition(), new RoundPoints(v.getRoundId(), v.getPoints())))
        .collect(Collectors.toList());
  }

}
