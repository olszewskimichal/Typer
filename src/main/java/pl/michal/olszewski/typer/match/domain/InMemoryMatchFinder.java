package pl.michal.olszewski.typer.match.domain;

import java.util.concurrent.ConcurrentHashMap;

class InMemoryMatchFinder implements MatchFinder {

  private ConcurrentHashMap<Long, Match> matchMap = new ConcurrentHashMap<>();

  @Override
  public Match findById(Long id) {
    return matchMap.get(id);
  }

  void save(Long id, Match match) {
    matchMap.put(id, match);
  }
}
