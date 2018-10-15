package pl.michal.olszewski.typer.bet.domain;

import java.util.Random;

class InMemoryBetRoundStatisticsSaver implements BetRoundStatisticsSaver {

  @Override
  public BetRoundStatistics save(BetRoundStatistics betStatisticsBase) {
    return InMemoryBetRoundStatisticsFinder.map.put(betStatisticsBase.getId() != null ? betStatisticsBase.getId() : new Random().nextLong(), betStatisticsBase);
  }

  @Override
  public void deleteAll() {
    InMemoryBetRoundStatisticsFinder.map.clear();
  }
}
