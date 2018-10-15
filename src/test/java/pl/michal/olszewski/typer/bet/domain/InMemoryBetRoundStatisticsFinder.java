package pl.michal.olszewski.typer.bet.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

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
  public Page<BetRoundStatistics> findByRoundIdOrderByPointsDesc(Long roundId, Pageable pageable) {
    return new PageImpl<>(
        findAll().stream()
            .filter(v -> v.getRoundId().equals(roundId))
            .sorted((v1, v2) -> v2.getPoints().compareTo(v1.getPoints()))
            .limit(pageable.getPageSize())
            .collect(Collectors.toList()));
  }

  @Override
  public List<BetRoundStatistics> findAll() {
    return new ArrayList<>(map.values());
  }
}
