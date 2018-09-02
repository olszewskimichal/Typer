package pl.michal.olszewski.typer.match.domain;

import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import pl.michal.olszewski.typer.match.dto.MatchNotFoundException;

@org.springframework.stereotype.Repository
interface MatchFinder extends Repository<Match, Long> {

  Match findById(Long id);


  @Query("select m from Match m where m.matchStatus=?1 and m.livescoreId is not null")
  List<Match> findByStatusForLivescoreUpdate(MatchStatus status);

  default Match findOneOrThrow(Long id) {
    Match match = findById(id);
    if (match == null) {
      throw new MatchNotFoundException(String.format("Mecz o id %s nie istnieje", id));
    }
    return match;
  }

  List<Match> findAll();

}
