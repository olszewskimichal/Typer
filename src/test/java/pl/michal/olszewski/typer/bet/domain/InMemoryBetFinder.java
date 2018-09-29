package pl.michal.olszewski.typer.bet.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import pl.michal.olszewski.typer.bet.dto.read.RoundPoints;

class InMemoryBetFinder implements BetFinder {

  static ConcurrentHashMap<Long, Bet> map = new ConcurrentHashMap<>();

  @Override
  public Bet findById(Long id) {
    return map.get(id);
  }

  @Override
  public List<Bet> findAllBetForMatch(Long matchId) {
    return map
        .values()
        .stream()
        .filter(v -> v.getMatchId().equals(matchId))
        .collect(Collectors.toList());
  }

  @Override
  public List<Bet> findAllBetForUser(Long userId) {
    return map
        .values()
        .stream()
        .filter(v -> v.getUserId().equals(userId))
        .collect(Collectors.toList());
  }

  @Override
  public List<Bet> findAllBetForRound(Long matchRoundId) {
    return map
        .values()
        .stream()
        .filter(v -> v.getMatchRoundId().equals(matchRoundId))
        .collect(Collectors.toList());
  }

  @Override
  public RoundPoints findSumOfPointsForRoundAndUser(Long userId, Long matchRoundId) {
    return null;
  }

  @Override
  public Long findSumOfPointsForLeagueAndUser(Long userId, Long leagueId) {
    return null;
  }

  @Override
  public List<Bet> findAll() {
    return new ArrayList<>(map.values());
  }

}
