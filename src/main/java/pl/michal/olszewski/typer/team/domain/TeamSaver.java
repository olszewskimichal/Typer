package pl.michal.olszewski.typer.team.domain;

import org.springframework.stereotype.Repository;

@Repository
interface TeamSaver extends org.springframework.data.repository.Repository<Team, Long> {

  Team save(Team user);

  void deleteAll();
}
