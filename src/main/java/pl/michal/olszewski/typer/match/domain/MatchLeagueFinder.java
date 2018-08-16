package pl.michal.olszewski.typer.match.domain;

import java.util.List;
import org.springframework.data.repository.Repository;
import pl.michal.olszewski.typer.match.dto.MatchLeagueNotFoundException;

@org.springframework.stereotype.Repository
interface MatchLeagueFinder extends Repository<MatchLeague, Long> {

  MatchLeague findById(Long id);

  default MatchLeague findOneOrThrow(Long id) {
    MatchLeague match = findById(id);
    if (match == null) {
      throw new MatchLeagueNotFoundException(String.format("Liga o id %s nie istnieje", id));
    }
    return match;
  }

  List<MatchLeague> findAll();

}
