package pl.michal.olszewski.typer.match.domain;

import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import pl.michal.olszewski.typer.match.dto.command.CreateNewLeague;

@Component
@Slf4j
class MatchLeagueCreator {

  MatchLeague from(CreateNewLeague createNewLeague) {
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
