package pl.michal.olszewski.typer.bet.domain;

import java.util.concurrent.ConcurrentHashMap;

class InMemoryBetFinder implements BetFinder {

  private ConcurrentHashMap<Long, Bet> map = new ConcurrentHashMap<>();

  @Override
  public Bet findById(Long id) {
    return map.get(id);
  }

  void save(Long id, Bet bet) {
    map.put(id, bet);
  }
}
