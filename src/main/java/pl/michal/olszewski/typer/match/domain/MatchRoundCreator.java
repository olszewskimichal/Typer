package pl.michal.olszewski.typer.match.domain;

import java.util.Objects;
import org.springframework.stereotype.Component;
import pl.michal.olszewski.typer.match.dto.command.CreateNewRound;

@Component
class MatchRoundCreator {

  private final MatchLeagueFinder matchLeagueFinder;

  MatchRoundCreator(MatchLeagueFinder matchLeagueFinder) {
    this.matchLeagueFinder = matchLeagueFinder;
  }

  MatchRound from(CreateNewRound createNewRound) {
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
