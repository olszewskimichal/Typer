package pl.michal.olszewski.typer.team.domain;

import java.util.Objects;
import org.springframework.stereotype.Component;
import pl.michal.olszewski.typer.team.dto.TeamExistsException;
import pl.michal.olszewski.typer.team.dto.command.CreateNewTeam;

@Component
class TeamCreator {

  private final TeamFinder teamFinder;

  TeamCreator(TeamFinder teamFinder) {
    this.teamFinder = teamFinder;
  }

  public Team from(CreateNewTeam command) {
    Objects.requireNonNull(command);
    command.validCommand();

    teamFinder.findByName(command.getName())
        .ifPresent(v -> {
          throw new TeamExistsException(String.format("Zespół o podanej nazwie %s już istnieje", v.getName()));
        });

    return Team.builder()
        .name(command.getName())
        .build();
  }
}
