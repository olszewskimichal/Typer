package pl.michal.olszewski.typer.bet.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

class InMemoryBetRoundStatisticsFinder implements BetRoundStatisticsFinder {

  static ConcurrentHashMap<Long, BetRoundStatistics> map = new ConcurrentHashMap<>();

  @Override
  public List<BetRoundStatistics> findByLeague(Long leagueId) {
    return new ArrayList<>(findAll());
  }

  @Override
  public Optional<BetRoundStatistics> findByUserIdAndRoundId(Long userId, Long roundId) {
    return findAll().stream().filter(v -> v.getUserId().equals(userId)).filter(v -> v.getRoundId().equals(roundId)).findAny();
  }

  @Override
  public List<BetRoundStatistics> findAll() {
    return new ArrayList<>(map.values());
  }
}
