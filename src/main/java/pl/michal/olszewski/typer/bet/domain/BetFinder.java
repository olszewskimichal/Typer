package pl.michal.olszewski.typer.bet.domain;

import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import pl.michal.olszewski.typer.bet.dto.BetNotFoundException;

@org.springframework.stereotype.Repository
interface BetFinder extends Repository<Bet, Long> {

  Bet findById(Long id);

  @Query("Select b from Bet b where b.matchId=?1")
  List<Bet> findAllBetForMatch(Long matchId);

  default Bet findOneOrThrow(Long id) {
    Bet bet = findById(id);
    if (bet == null) {
      throw new BetNotFoundException(String.format("Zakład o id %s nie istnieje", id));
    }
    return bet;
  }

}
