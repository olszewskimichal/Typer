package pl.michal.olszewski.typer.team.domain;

import java.util.Random;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ActiveProfiles;

@Configuration
@ActiveProfiles("test")
class InMemoryTeamSaver implements TeamSaver {


  @Override
  public Team save(Team team) {
    return InMemoryTeamFinder.map.put(team.getId() != null ? team.getId() : new Random().nextLong(), team);
  }

  @Override
  public void deleteAll() {
    InMemoryTeamFinder.map.clear();
  }
}
