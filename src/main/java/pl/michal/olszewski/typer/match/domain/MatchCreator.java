package pl.michal.olszewski.typer.match.domain;

import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import pl.michal.olszewski.typer.match.dto.command.CreateNewMatch;

@Slf4j
class MatchCreator {

  private MatchCreator() {
  }

  static Match from(CreateNewMatch createNewMatch, MatchRoundFinder matchRoundFinder) {
    log.debug("Creating match from command {}", createNewMatch);
    Objects.requireNonNull(createNewMatch);
    createNewMatch.validCommand();

    MatchRound round = matchRoundFinder.findOneOrThrow(createNewMatch.getRoundId());
    Match match = Match.builder()
        .homeTeamId(createNewMatch.getHomeTeamId())
        .awayTeamId(createNewMatch.getAwayTeamId())
        .matchStatus(MatchStatus.NEW)
        .startDate(createNewMatch.getStartDate())
        .build();
    round.addMatch(match);
    return match;
  }

}
