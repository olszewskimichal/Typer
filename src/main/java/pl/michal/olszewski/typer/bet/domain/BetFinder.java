package pl.michal.olszewski.typer.bet.domain;

import java.time.Instant;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.transaction.annotation.Transactional;
import pl.michal.olszewski.typer.bet.dto.BetNotFoundException;

/**
 * select  USER_ID,sum(points) from BET group by USER_ID
 * select USER_ID,MATCH_ROUND_ID,sum(points) from BET group by USER_ID,MATCH_ROUND_ID order by USER_ID
 * select USER_ID,sum(points) from BET group by USER_ID order by sum(points) DESC
 * select USER_ID,sum(points) from BET where MATCH_ROUND_ID in ( select id from MATCH_ROUND where LEAGUE_ID=1) group by USER_ID
 */
@org.springframework.stereotype.Repository
@Transactional(readOnly = true)
interface BetFinder extends Repository<Bet, Long> {

  Bet findById(Long id);

  @Query("Select b from Bet b where b.matchId=?1")
  List<Bet> findAllBetForMatch(Long matchId);

  @Query("Select b from Bet b where b.userId=?1")
  List<Bet> findAllBetForUser(Long userId);

  @Query("Select b from Bet b where b.status=2 and b.matchRoundId=?1")
  List<Bet> findAllFinishedBetForRound(Long matchRoundId);

  List<Bet> findByModifiedAfter(Instant from);

  default Bet findOneOrThrow(Long id) {
    Bet bet = findById(id);
    if (bet == null) {
      throw new BetNotFoundException(String.format("Zakład o id %s nie istnieje", id));
    }
    return bet;
  }

  List<Bet> findAll();

}
