package pl.michal.olszewski.typer.bet.domain;

import java.util.Random;

class InMemoryBetLeagueStatisticsSaver implements BetLeagueStatisticsSaver {

  @Override
  public BetLeagueStatistics save(BetLeagueStatistics betStatisticsBase) {
    return InMemoryBetLeagueStatisticsFinder.map.put(betStatisticsBase.getId() != null ? betStatisticsBase.getId() : new Random().nextLong(), betStatisticsBase);
  }

  @Override
  public void deleteAll() {
    InMemoryBetLeagueStatisticsFinder.map.clear();
  }
}
