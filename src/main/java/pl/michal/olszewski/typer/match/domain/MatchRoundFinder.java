package pl.michal.olszewski.typer.match.domain;

import org.springframework.data.repository.Repository;
import pl.michal.olszewski.typer.match.dto.MatchNotFoundException;
import pl.michal.olszewski.typer.match.dto.MatchRoundNotFoundException;

@org.springframework.stereotype.Repository
interface MatchRoundFinder extends Repository<MatchRound, Long> {

  MatchRound findById(Long id);

  default MatchRound findOneOrThrow(Long id) {
    MatchRound match = findById(id);
    if (match == null) {
      throw new MatchRoundNotFoundException(String.format("Kolejka ligowa o id %s nie istnieje", id));
    }
    return match;
  }

}
