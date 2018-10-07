package pl.michal.olszewski.typer.bet.domain;

import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.transaction.annotation.Transactional;


@org.springframework.stereotype.Repository
@Transactional(readOnly = true)
interface BetStatisticsFinder extends Repository<BetStatisticsBase, Long> {

  List<BetStatisticsBase> findAll();

  @Query("Select b from BetRoundStatistics b where b.roundId in (select m.id from MatchRound m where m.matchLeague.id=?1)")
  List<BetRoundStatistics> findByLeague(Long leagueId);
}
