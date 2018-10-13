package pl.michal.olszewski.typer.bet.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

class InMemoryBetLeagueStatisticsFinder implements BetLeagueStatisticsFinder {

  static ConcurrentHashMap<Long, BetLeagueStatistics> map = new ConcurrentHashMap<>();

  public List<BetLeagueStatistics> findAll() {
    return new ArrayList<>(map.values());
  }

  public Optional<BetLeagueStatistics> findByUserIdAndLeagueId(Long userId, Long leagueId) {
    return findAll().stream().filter(v -> v.getUserId().equals(userId)).filter(v -> v.getLeagueId().equals(leagueId)).findAny();
  }

}
