package pl.michal.olszewski.typer.match.domain;

import java.util.Random;

class InMemoryMatchSaver implements MatchSaver {


  @Override
  public Match save(Match match) {
    return InMemoryMatchFinder.map.put(match.getId() != null ? match.getId() : new Random().nextLong(), match);
  }

  @Override
  public void deleteAll() {
    InMemoryMatchFinder.map.clear();
  }
}
