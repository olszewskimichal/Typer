package pl.michal.olszewski.typer.match.domain;

import java.util.Objects;
import pl.michal.olszewski.typer.match.dto.command.CreateNewMatch;

class MatchCreator {

  Match from(CreateNewMatch createNewMatch) {
    Objects.requireNonNull(createNewMatch);
    createNewMatch.validCommand();

    return Match.builder()
        .homeTeamId(createNewMatch.getHomeTeamId())
        .awayTeamId(createNewMatch.getAwayTeamId())
        .build();
  }

}
