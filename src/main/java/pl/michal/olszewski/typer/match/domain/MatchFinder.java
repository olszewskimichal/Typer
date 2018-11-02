package pl.michal.olszewski.typer.match.domain;

import java.util.List;
import java.util.Optional;
import org.springframework.data.repository.Repository;
import pl.michal.olszewski.typer.match.dto.MatchNotFoundException;

@org.springframework.stereotype.Repository
interface MatchFinder extends Repository<Match, Long> {

  Optional<Match> findById(Long id);

  default Match findOneOrThrow(Long id) {
    return findById(id)
        .orElseThrow(() -> new MatchNotFoundException(String.format("Mecz o id %s nie istnieje", id)));
  }

  List<Match> findAll();

}
