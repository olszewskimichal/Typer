package pl.michal.olszewski.typer.bet.domain;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

@org.springframework.stereotype.Repository
interface BetLeagueStatisticsFinder extends BetStatisticsFinder<BetLeagueStatistics> {


  @Query("Select b from BetLeagueStatistics b where b.userId=?1 and b.leagueId=?2")
  Optional<BetLeagueStatistics> findByUserIdAndLeagueId(Long userId, Long leagueId);

  Page<BetLeagueStatistics> findByLeagueIdOrderByPointsDesc(Long leagueId, Pageable pageable);
}
