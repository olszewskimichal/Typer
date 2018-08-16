package pl.michal.olszewski.typer.match.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

class InMemoryMatchFinder implements MatchFinder {

  private ConcurrentHashMap<Long, Match> matchMap = new ConcurrentHashMap<>();

  @Override
  public Match findById(Long id) {
    return matchMap.get(id);
  }

  @Override
  public List<Match> findAll() {
    return new ArrayList<>(matchMap.values());
  }

  void save(Long id, Match match) {
    matchMap.put(id, match);
  }
}
