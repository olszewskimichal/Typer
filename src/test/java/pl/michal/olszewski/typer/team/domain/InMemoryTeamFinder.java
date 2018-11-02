package pl.michal.olszewski.typer.team.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ActiveProfiles;

@Configuration
@ActiveProfiles("test")
class InMemoryTeamFinder implements TeamFinder {

  static ConcurrentHashMap<Long, Team> map = new ConcurrentHashMap<>();

  @Override
  public Optional<Team> findByName(String name) {
    return map.values().stream().filter(v -> v.getName().equalsIgnoreCase(name)).findAny();
  }

  @Override
  public List<Team> findAll() {
    return new ArrayList<>(map.values());
  }


  @Override
  public Team findById(Long id) {
    return map.get(id);
  }
}
