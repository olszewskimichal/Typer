package pl.michal.olszewski.typer.team.domain;

import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

class InMemoryTeamFinder implements TeamFinder {

  static ConcurrentHashMap<Long, Team> map = new ConcurrentHashMap<>();


  @Override
  public Optional<Team> findByName(String name) {
    return map.values().stream().filter(v -> v.getName().equalsIgnoreCase(name)).findAny();
  }
}
