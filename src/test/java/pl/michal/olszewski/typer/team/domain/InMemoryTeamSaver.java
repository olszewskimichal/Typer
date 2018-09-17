package pl.michal.olszewski.typer.team.domain;

import java.util.Random;

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
