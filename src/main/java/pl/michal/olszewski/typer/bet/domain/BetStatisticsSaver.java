package pl.michal.olszewski.typer.bet.domain;

import org.springframework.stereotype.Repository;

@Repository
interface BetStatisticsSaver extends org.springframework.data.repository.Repository<BetStatisticsBase, Long> {

    BetStatisticsBase save(BetStatisticsBase bet);

    void deleteAll();
}
