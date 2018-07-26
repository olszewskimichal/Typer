package pl.michal.olszewski.typer.match.domain;

import java.util.Objects;
import pl.michal.olszewski.typer.match.dto.CreateNewMatch;
import pl.michal.olszewski.typer.match.dto.IllegalMatchMemberException;

class MatchCreator {

  Match from(CreateNewMatch createNewMatch) {
    Objects.requireNonNull(createNewMatch);
    if (createNewMatch.getAwayTeamId() == null || createNewMatch.getHomeTeamId() == null) {
      throw new IllegalMatchMemberException("One of team not exists");
    }
    if (createNewMatch.getHomeTeamId().equals(createNewMatch.getAwayTeamId())) {
      throw new IllegalMatchMemberException("You cannot create match with two the same teams");
    }
    return Match.builder()
        .homeTeamId(createNewMatch.getHomeTeamId())
        .awayTeamId(createNewMatch.getAwayTeamId())
        .build();
  }

}
