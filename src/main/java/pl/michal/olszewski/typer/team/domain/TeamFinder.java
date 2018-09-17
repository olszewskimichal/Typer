package pl.michal.olszewski.typer.team.domain;

import java.util.Optional;
import org.springframework.data.repository.Repository;

@org.springframework.stereotype.Repository
interface TeamFinder extends Repository<Team, Long> {

  Optional<Team> findByName(String name);

}
