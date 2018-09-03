package pl.michal.olszewski.typer.match.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

class InMemoryMatchFinder implements MatchFinder {

  static ConcurrentHashMap<Long, Match> map = new ConcurrentHashMap<>();

  @Override
  public Match findById(Long id) {
    return map.get(id);
  }

  @Override
  public List<Match> findByStatusForLivescoreUpdate(MatchStatus status) {
    return map.values().stream().filter(v -> v.getMatchStatus().equals(status)).filter(v -> v.getLivescoreId() != null).collect(Collectors.toList());
  }

  @Override
  public List<Match> findAll() {
    return new ArrayList<>(map.values());
  }

}