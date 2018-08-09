package pl.michal.olszewski.typer.match.domain;

import java.util.concurrent.ConcurrentHashMap;

class InMemoryMatchRoundFinder implements MatchRoundFinder {

  private ConcurrentHashMap<Long, MatchRound> matchMap = new ConcurrentHashMap<>();

  @Override
  public MatchRound findById(Long id) {
    return matchMap.get(id);
  }

  void save(Long id, MatchRound match) {
    matchMap.put(id, match);
  }
}
