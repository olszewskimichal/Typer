package pl.michal.olszewski.typer.bet.domain;

import java.util.Random;

class InMemoryBetStatisticsSaver implements BetStatisticsSaver {


    @Override
    public BetStatisticsBase save(BetStatisticsBase betStatisticsBase) {
        return InMemoryBetStatisticsFinder.map.put(betStatisticsBase.getId() != null ? betStatisticsBase.getId() : new Random().nextLong(), betStatisticsBase);
    }

    @Override
    public void deleteAll() {
        InMemoryBetStatisticsFinder.map.clear();
    }
}
