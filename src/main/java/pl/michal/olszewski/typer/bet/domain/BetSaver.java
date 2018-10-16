package pl.michal.olszewski.typer.bet.domain;

import org.springframework.stereotype.Repository;

@Repository
interface BetSaver extends org.springframework.data.repository.Repository<Bet, Long> {

  Bet save(Bet bet);

  void deleteAll();
}
