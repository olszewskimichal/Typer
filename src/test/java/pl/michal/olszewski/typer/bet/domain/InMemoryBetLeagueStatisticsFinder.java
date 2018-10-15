package pl.michal.olszewski.typer.bet.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

class InMemoryBetLeagueStatisticsFinder implements BetLeagueStatisticsFinder {

  static ConcurrentHashMap<Long, BetLeagueStatistics> map = new ConcurrentHashMap<>();

  public List<BetLeagueStatistics> findAll() {
    return new ArrayList<>(map.values());
  }

  public Optional<BetLeagueStatistics> findByUserIdAndLeagueId(Long userId, Long leagueId) {
    return findAll().stream().filter(v -> v.getUserId().equals(userId)).filter(v -> v.getLeagueId().equals(leagueId)).findAny();
  }

  @Override
  public Page<BetLeagueStatistics> findByLeagueIdOrderByPointsDesc(Long leagueId, Pageable pageable) {
    return new PageImpl<>(
        findAll().stream()
            .filter(v -> v.getLeagueId().equals(leagueId))
            .sorted((v1, v2) -> v2.getPoints().compareTo(v1.getPoints()))
            .limit(pageable.getPageSize())
            .collect(Collectors.toList()));
  }

}
