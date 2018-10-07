package pl.michal.olszewski.typer.bet.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

class InMemoryBetStatisticsFinder implements BetStatisticsFinder {

    static ConcurrentHashMap<Long, BetStatisticsBase> map = new ConcurrentHashMap<>();

    @Override
    public List<BetStatisticsBase> findAll() {
        return new ArrayList<>(map.values());
    }

    @Override
    public List<BetRoundStatistics> findByLeague(Long leagueId) {
        return findAll().stream().filter(v -> v instanceof BetRoundStatistics).map(v -> (BetRoundStatistics) v).collect(Collectors.toList());
    }

}
