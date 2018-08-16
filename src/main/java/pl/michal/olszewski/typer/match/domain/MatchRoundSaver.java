package pl.michal.olszewski.typer.match.domain;

import org.springframework.stereotype.Repository;

@Repository
interface MatchRoundSaver extends org.springframework.data.repository.Repository<MatchRound, Long> {

  MatchRound save(MatchRound matchRound);

  void deleteAll();
}
