package pl.michal.olszewski.typer.bet.domain;

import org.springframework.data.repository.Repository;

@org.springframework.stereotype.Repository
interface BetFinder extends Repository<Bet, Long> {

  Bet findById(Long id);

  default Bet findOneOrThrow(Long id) {
    Bet bet = findById(id);
    if (bet == null) {
      throw new BetNotFoundException(String.format("Zakład o id %s nie istnieje", id));
    }
    return bet;
  }

}
