package pl.michal.olszewski.typer.match.domain;

import org.springframework.stereotype.Repository;

@Repository
interface MatchLeagueSaver extends org.springframework.data.repository.Repository<MatchLeague, Long> {

  MatchLeague save(MatchLeague matchLeague);

  void deleteAll();
}
