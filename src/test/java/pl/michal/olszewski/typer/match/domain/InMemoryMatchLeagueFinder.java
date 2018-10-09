package pl.michal.olszewski.typer.match.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class InMemoryMatchLeagueFinder implements MatchLeagueFinder {

  static ConcurrentHashMap<Long, MatchLeague> map = new ConcurrentHashMap<>();

  @Override
  public MatchLeague findById(Long id) {
    return map.get(id);
  }

  @Override
  public List<MatchLeague> findAll() {
    return new ArrayList<>(map.values());
  }

  @Override
  public List<Long> findLeagueIdsForRounds(Set<Long> roundIds) {
    return map.values().stream().map(MatchLeague::getId).collect(Collectors.toList());
  }

}
