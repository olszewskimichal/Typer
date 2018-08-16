package pl.michal.olszewski.typer.match.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

class InMemoryMatchRoundFinder implements MatchRoundFinder {

  static ConcurrentHashMap<Long, MatchRound> map = new ConcurrentHashMap<>();

  @Override
  public MatchRound findById(Long id) {
    return map.get(id);
  }

  @Override
  public List<MatchRound> findAll() {
    return new ArrayList<>(map.values());
  }

}
