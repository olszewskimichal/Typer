package pl.michal.olszewski.typer.bet.domain;

import java.util.Random;

class InMemoryBetSaver implements BetSaver {


  @Override
  public Bet save(Bet bet) {
    return InMemoryBetFinder.map.put(bet.getId() != null ? bet.getId() : new Random().nextLong(), bet);
  }

  @Override
  public void deleteAll() {
    InMemoryBetFinder.map.clear();
  }
}
