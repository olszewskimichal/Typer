package pl.michal.olszewski.typer.bet.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

class InMemoryBetStatisticsFinder implements BetStatisticsFinder {

  static ConcurrentHashMap<Long, BetStatisticsBase> map = new ConcurrentHashMap<>();

  @Override
  public List<BetStatisticsBase> findAll() {
    return new ArrayList<>(map.values());
  }

  private List<BetRoundStatistics> findAllRoundStats() {
    return findAll().stream().filter(v -> v instanceof BetRoundStatistics).map(v -> (BetRoundStatistics) v).collect(Collectors.toList());
  }

  private List<BetLeagueStatistics> findAllLeagueStats() {
    return findAll().stream().filter(v -> v instanceof BetLeagueStatistics).map(v -> (BetLeagueStatistics) v).collect(Collectors.toList());
  }

  @Override
  public List<BetRoundStatistics> findByLeague(Long leagueId) {
    return findAll().stream().filter(v -> v instanceof BetRoundStatistics).map(v -> (BetRoundStatistics) v).collect(Collectors.toList());
  }

  @Override
  public Optional<BetRoundStatistics> findByUserIdAndRoundId(Long userId, Long roundId) {
    return findAllRoundStats().stream().filter(v -> v.getUserId().equals(userId)).filter(v -> v.getRoundId().equals(roundId)).findAny();
  }

  @Override
  public Optional<BetLeagueStatistics> findByUserIdAndLeagueId(Long userId, Long leagueId) {
    return findAllLeagueStats().stream().filter(v -> v.getUserId().equals(userId)).filter(v -> v.getLeagueId().equals(leagueId)).findAny();
  }

}
