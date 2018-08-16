package pl.michal.olszewski.typer.match.domain;

import java.util.Random;

class InMemoryMatchLeagueSaver implements MatchLeagueSaver {


  @Override
  public MatchLeague save(MatchLeague matchLeague) {
    return InMemoryMatchLeagueFinder.map.put(matchLeague.getId() != null ? matchLeague.getId() : new Random().nextLong(), matchLeague);
  }

  @Override
  public void deleteAll() {
    InMemoryMatchLeagueFinder.map.clear();
  }
}
