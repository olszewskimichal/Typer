package pl.michal.olszewski.typer.bet.domain;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
class BetStatisticsCalculator {

  private final BetRoundStatisticsSaver betRoundStatisticsSaver;
  private final BetLeagueStatisticsSaver betLeagueStatisticsSaver;
  private final BetRoundStatisticsFinder betRoundStatisticsFinder;
  private final BetLeagueStatisticsFinder betLeagueStatisticsFinder;
  private final BetFinder betFinder;

  public BetStatisticsCalculator(BetRoundStatisticsSaver betRoundStatisticsSaver, BetLeagueStatisticsSaver betLeagueStatisticsSaver,
      BetRoundStatisticsFinder betRoundStatisticsFinder, BetLeagueStatisticsFinder betLeagueStatisticsFinder, BetFinder betFinder) {
    this.betRoundStatisticsSaver = betRoundStatisticsSaver;
    this.betLeagueStatisticsSaver = betLeagueStatisticsSaver;
    this.betRoundStatisticsFinder = betRoundStatisticsFinder;
    this.betLeagueStatisticsFinder = betLeagueStatisticsFinder;
    this.betFinder = betFinder;
  }

  void calculateStatisticsForLeague(Long leagueId) {
    log.debug("Rozpoczynam naliczanie statystyk dla ligi {}", leagueId);
    List<BetRoundStatistics> roundStatistics = betRoundStatisticsFinder.findByLeague(leagueId);
    Map<Long, List<BetRoundStatistics>> roundStatsPerUser = roundStatistics.stream().collect(Collectors.groupingBy(BetStatisticsBase::getUserId));
    List<UserPoints> userPoints = roundStatsPerUser.entrySet()
        .stream()
        .sorted((v1, v2) -> sumOfPointsStats(v2.getValue()).compareTo(sumOfPointsStats(v1.getValue())))
        .map(v -> new UserPoints(v.getKey(), sumOfPointsStats(v.getValue())))
        .collect(Collectors.toList());

    calculateAndSave(null, leagueId, userPoints);
  }

  private void calculateAndSave(Long roundId, Long leagueId, @NonNull List<UserPoints> userPoints) {
    if (userPoints.size() < 2) {
      log.trace("Mamy mniej niz 2 statystyki do naliczenia");
      for (UserPoints userPoint : userPoints) {
        saveStatsToDB(roundId, leagueId, userPoint, 1L);
      }
    } else {
      log.trace("Mamy wiecej niz 2 statystyki do naliczenia, dokladnie {} ", userPoints.size());
      Iterator<UserPoints> iter = userPoints.iterator();
      UserPoints first = iter.next();
      long position = 1L;
      long positionTmp = 1L;
      saveStatsToDB(roundId, leagueId, first, position);
      while (iter.hasNext()) {
        UserPoints second = iter.next();
        if (first.getPoints().equals(second.getPoints())) {
          positionTmp++;
          saveStatsToDB(roundId, leagueId, second, position);
        } else {
          first = second;
          position = ++positionTmp;
          saveStatsToDB(roundId, leagueId, second, position);
        }
      }
    }
  }

  void calculateStatisticsForRound(Long roundId) {
    log.debug("Rozpoczynam naliczanie statystyk dla rundy {}", roundId);
    List<Bet> bets = betFinder.findAllFinishedBetForRound(roundId);
    log.trace("Pobralem {} zakladów do naliczenia", bets.size());
    Map<Long, List<Bet>> betsPerUser = bets.stream().collect(Collectors.groupingBy(Bet::getUserId));
    List<UserPoints> userPoints = betsPerUser.entrySet()
        .stream()
        .sorted((v1, v2) -> sumOfPoints(v2.getValue()).compareTo(sumOfPoints(v1.getValue())))
        .map(v -> new UserPoints(v.getKey(), sumOfPoints(v.getValue())))
        .collect(Collectors.toList());
    calculateAndSave(roundId, null, userPoints);
  }

  private void saveStatsToDB(Long roundId, Long leagueId, UserPoints userPoints, long position) {
    if (roundId != null) {
      BetRoundStatistics betRoundStatistics = betRoundStatisticsFinder.findByUserIdAndRoundId(userPoints.userId, roundId).orElse(new BetRoundStatistics());
      BetRoundStatistics newRoundStats = BetRoundStatistics.builder()
          .points(userPoints.getPoints())
          .position(position)
          .roundId(roundId)
          .userId(userPoints.getUserId())
          .build();
      newRoundStats.setId(betRoundStatistics.getId());
      log.trace("Zapisuje statystyke dla uzytkownika {} rundy {} z pozycja {} punktami {}", userPoints.userId, roundId, position, userPoints.points);
      betRoundStatisticsSaver.save(newRoundStats);
    }
    if (leagueId != null) {
      BetLeagueStatistics betLeagueStatistics = betLeagueStatisticsFinder.findByUserIdAndLeagueId(userPoints.userId, leagueId).orElse(new BetLeagueStatistics());

      BetLeagueStatistics build = BetLeagueStatistics.builder()
          .points(userPoints.getPoints())
          .position(position)
          .leagueId(leagueId)
          .userId(userPoints.getUserId())
          .build();
      build.setId(betLeagueStatistics.getId());
      log.trace("Zapisuje statystyke dla uzytkownika {} ligi {} z pozycja {} punktami {}", userPoints.userId, leagueId, position, userPoints.points);
      betLeagueStatisticsSaver.save(build);
    }
  }


  private Long sumOfPoints(List<Bet> bets) {
    return bets.stream().mapToLong(Bet::getPoints).sum();
  }

  private Long sumOfPointsStats(List<BetRoundStatistics> statistics) {
    return statistics.stream().mapToLong(BetRoundStatistics::getPoints).sum();
  }

  @AllArgsConstructor
  @Getter
  private final class UserPoints {

    private final Long userId;
    private final Long points;
  }
}

