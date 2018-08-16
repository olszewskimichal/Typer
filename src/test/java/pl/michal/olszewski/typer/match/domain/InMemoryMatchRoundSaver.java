package pl.michal.olszewski.typer.match.domain;

import java.util.Random;

class InMemoryMatchRoundSaver implements MatchRoundSaver {


  @Override
  public MatchRound save(MatchRound matchRound) {
    return InMemoryMatchRoundFinder.map.put(matchRound.getId() != null ? matchRound.getId() : new Random().nextLong(), matchRound);
  }

  @Override
  public void deleteAll() {
    InMemoryMatchRoundFinder.map.clear();
  }
}
