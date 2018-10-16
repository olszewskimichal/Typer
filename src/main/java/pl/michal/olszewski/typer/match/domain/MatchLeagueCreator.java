package pl.michal.olszewski.typer.match.domain;

import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import pl.michal.olszewski.typer.match.dto.command.CreateNewLeague;

@Slf4j
class MatchLeagueCreator {

  private MatchLeagueCreator() {
  }

  static MatchLeague from(CreateNewLeague createNewLeague) {
    log.debug("Creating league from command {}", createNewLeague);
    Objects.requireNonNull(createNewLeague);
    createNewLeague.validCommand();

    return MatchLeague
        .builder()
        .name(createNewLeague.getName())
        .betTypePolicy(createNewLeague.getBetTypePolicy())
        .build();
  }

}
