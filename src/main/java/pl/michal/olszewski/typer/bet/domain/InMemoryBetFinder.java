package pl.michal.olszewski.typer.bet.domain;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

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
        .peek(System.out::println)
        .filter(v -> v.getMatchId().equals(matchId))
        .collect(Collectors.toList());
  }

}
