package pl.michal.olszewski.typer.match.domain;

import java.util.Objects;
import pl.michal.olszewski.typer.match.dto.command.CreateNewLeague;

class MatchLeagueCreator {

  MatchLeague from(CreateNewLeague createNewLeague) {
    Objects.requireNonNull(createNewLeague);
    createNewLeague.validCommand();

    return MatchLeague
        .builder()
        .name(createNewLeague.getName())
        .betTypePolicy(createNewLeague.getBetTypePolicy())
        .build();
  }

}
