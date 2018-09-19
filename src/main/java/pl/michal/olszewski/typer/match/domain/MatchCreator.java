package pl.michal.olszewski.typer.match.domain;

import java.util.Objects;

import org.springframework.stereotype.Component;
import pl.michal.olszewski.typer.match.dto.command.CreateNewMatch;

@Component
class MatchCreator {

  private final MatchRoundFinder matchRoundFinder;

  MatchCreator(MatchRoundFinder matchRoundFinder) {
    this.matchRoundFinder = matchRoundFinder;
  }

  Match from(CreateNewMatch createNewMatch) {
    Objects.requireNonNull(createNewMatch);
    createNewMatch.validCommand();

    MatchRound round = matchRoundFinder.findOneOrThrow(createNewMatch.getRoundId());
    Match match = Match.builder()
        .homeTeamId(createNewMatch.getHomeTeamId())
        .awayTeamId(createNewMatch.getAwayTeamId())
        .matchStatus(MatchStatus.NEW)
        .build();
    round.addMatch(match);
    return match;
  }

}
