package pl.michal.olszewski.typer.match.domain;

import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import pl.michal.olszewski.typer.match.dto.command.CreateNewRound;

@Slf4j
class MatchRoundCreator {

  static MatchRound from(CreateNewRound createNewRound, MatchLeagueFinder matchLeagueFinder) {
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
