package pl.michal.olszewski.typer.match.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

class InMemoryMatchFinder implements MatchFinder {

  static ConcurrentHashMap<Long, Match> map = new ConcurrentHashMap<>();

  @Override
  public Optional<Match> findById(Long id) {
    return Optional.ofNullable(map.get(id));
  }

  @Override
  public Optional<Match> findByLivescoreId(Long livescoreId) {
    return map.values().stream().filter(v -> v.getLivescoreId().equals(livescoreId)).findAny();
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
