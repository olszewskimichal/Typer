package pl.michal.olszewski.typer.match.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

class InMemoryMatchRoundFinder implements MatchRoundFinder {

  private ConcurrentHashMap<Long, MatchRound> matchMap = new ConcurrentHashMap<>();

  @Override
  public MatchRound findById(Long id) {
    return matchMap.get(id);
  }

  @Override
  public List<MatchRound> findAll() {
    return new ArrayList<>(matchMap.values());
  }

  void save(Long id, MatchRound match) {
    matchMap.put(id, match);
  }
}
