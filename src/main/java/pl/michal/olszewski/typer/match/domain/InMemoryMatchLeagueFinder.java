package pl.michal.olszewski.typer.match.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

class InMemoryMatchLeagueFinder implements MatchLeagueFinder {

  private ConcurrentHashMap<Long, MatchLeague> matchMap = new ConcurrentHashMap<>();

  @Override
  public MatchLeague findById(Long id) {
    return matchMap.get(id);
  }

  @Override
  public List<MatchLeague> findAll() {
    return new ArrayList<>(matchMap.values());
  }

  void save(Long id, MatchLeague match) {
    matchMap.put(id, match);
  }
}
