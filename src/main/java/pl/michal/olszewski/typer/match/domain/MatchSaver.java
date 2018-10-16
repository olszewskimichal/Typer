package pl.michal.olszewski.typer.match.domain;

import org.springframework.stereotype.Repository;

@Repository
interface MatchSaver extends org.springframework.data.repository.Repository<Match, Long> {

  Match save(Match match);

  void deleteAll();
}
