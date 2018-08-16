package pl.michal.olszewski.typer.match.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

class InMemoryMatchLeagueFinder implements MatchLeagueFinder {

  static ConcurrentHashMap<Long, MatchLeague> map = new ConcurrentHashMap<>();

  @Override
  public MatchLeague findById(Long id) {
    return map.get(id);
  }

  @Override
  public List<MatchLeague> findAll() {
    return new ArrayList<>(map.values());
  }

}
