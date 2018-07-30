package pl.michal.olszewski.typer.match.domain;

import org.springframework.data.repository.Repository;
import pl.michal.olszewski.typer.match.dto.MatchNotFoundException;

@org.springframework.stereotype.Repository
interface MatchFinder extends Repository<Match, Long> {

  Match findById(Long id);

  default Match findOneOrThrow(Long id) {
    Match match = findById(id);
    if (match == null) {
      throw new MatchNotFoundException(String.format("Mecz o id %s nie istnieje", id));
    }
    return match;
  }

}
