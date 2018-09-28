package pl.michal.olszewski.typer.bet.domain;

import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.transaction.annotation.Transactional;
import pl.michal.olszewski.typer.bet.dto.BetNotFoundException;

@org.springframework.stereotype.Repository
@Transactional(readOnly = true)
interface BetFinder extends Repository<Bet, Long> {

  Bet findById(Long id);

  @Query("Select b from Bet b where b.matchId=?1")
  List<Bet> findAllBetForMatch(Long matchId);

  @Query("Select b from Bet b where b.userId=?1")
  List<Bet> findAllBetForUser(Long userId);

  @Query("Select b from Bet b where b.matchRoundId=?1")
  List<Bet> findAllBetForRound(Long matchRoundId);

  default Bet findOneOrThrow(Long id) {
    Bet bet = findById(id);
    if (bet == null) {
      throw new BetNotFoundException(String.format("Zakład o id %s nie istnieje", id));
    }
    return bet;
  }

  List<Bet> findAll();

}
