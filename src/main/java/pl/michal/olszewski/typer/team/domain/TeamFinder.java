package pl.michal.olszewski.typer.team.domain;

import java.util.List;
import java.util.Optional;
import org.springframework.data.repository.Repository;
import pl.michal.olszewski.typer.team.dto.TeamNotFoundException;

@org.springframework.stereotype.Repository
interface TeamFinder extends Repository<Team, Long> {

  Optional<Team> findByName(String name);

  List<Team> findAll();

  Team findById(Long id);

  default Team findOneOrThrow(Long id) {
    Team team = findById(id);
    if (team == null) {
      throw new TeamNotFoundException(String.format("Zespol o id %s nie istnieje", id));
    }
    return team;
  }
}
