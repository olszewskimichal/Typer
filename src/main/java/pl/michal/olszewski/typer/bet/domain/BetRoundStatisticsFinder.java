package pl.michal.olszewski.typer.bet.domain;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;


@org.springframework.stereotype.Repository
interface BetRoundStatisticsFinder extends BetStatisticsFinder<BetRoundStatistics> {

  @Query("Select b from BetRoundStatistics b where b.roundId in (select m.id from MatchRound m where m.matchLeague.id=?1)")
  List<BetRoundStatistics> findByLeague(Long leagueId);

  @Query("Select b from BetRoundStatistics b where b.userId=?1 and b.roundId=?2")
  Optional<BetRoundStatistics> findByUserIdAndRoundId(Long userId, Long roundId);

  Page<BetRoundStatistics> findByRoundIdOrderByPointsDesc(Long roundId, Pageable pageable);

}
