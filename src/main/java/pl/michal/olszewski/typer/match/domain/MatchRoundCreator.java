package pl.michal.olszewski.typer.match.domain;

import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import pl.michal.olszewski.typer.match.dto.command.CreateNewRound;

@Component
@Slf4j
class MatchRoundCreator {

  private final MatchLeagueFinder matchLeagueFinder;

  MatchRoundCreator(MatchLeagueFinder matchLeagueFinder) {
    this.matchLeagueFinder = matchLeagueFinder;
  }

  MatchRound from(CreateNewRound createNewRound) {
    log.debug("Creating round from command {}", createNewRound);
    Objects.requireNonNull(createNewRound);
    createNewRound.validCommand();

    MatchRound matchRound = MatchRound
        .builder()
        .name(createNewRound.getName())
        .build();

    MatchLeague league = matchLeagueFinder.findOneOrThrow(createNewRound.getLeagueId());
    league.addMatchRound(matchRound);

    return matchRound;
  }

}
