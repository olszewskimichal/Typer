package pl.michal.olszewski.typer.bet.domain;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.transaction.annotation.Transactional;


@org.springframework.stereotype.Repository
@Transactional(readOnly = true)
interface BetStatisticsFinder extends Repository<BetStatisticsBase, Long> {

  List<BetStatisticsBase> findAll();

  @Query("Select b from BetRoundStatistics b where b.roundId in (select m.id from MatchRound m where m.matchLeague.id=?1)")
  List<BetRoundStatistics> findByLeague(Long leagueId);

  @Query("Select b from BetRoundStatistics b where b.userId=?1 and b.roundId=?2")
  Optional<BetRoundStatistics> findByUserIdAndRoundId(Long userId, Long roundId);

  @Query("Select b from BetLeagueStatistics b where b.userId=?1 and b.leagueId=?2")
  Optional<BetLeagueStatistics> findByUserIdAndLeagueId(Long userId, Long leagueId);
}
