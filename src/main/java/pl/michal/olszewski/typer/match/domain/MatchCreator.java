package pl.michal.olszewski.typer.match.domain;

import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import pl.michal.olszewski.typer.match.dto.command.CreateNewMatch;

@Component
@Slf4j
class MatchCreator {

  private final MatchRoundFinder matchRoundFinder;

  MatchCreator(MatchRoundFinder matchRoundFinder) {
    this.matchRoundFinder = matchRoundFinder;
  }

  Match from(CreateNewMatch createNewMatch) {
    log.debug("Creating match from command {}", createNewMatch);
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
