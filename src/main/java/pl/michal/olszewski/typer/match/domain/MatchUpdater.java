package pl.michal.olszewski.typer.match.domain;

import java.util.Objects;
import org.springframework.stereotype.Component;
import pl.michal.olszewski.typer.match.dto.command.CancelMatch;
import pl.michal.olszewski.typer.match.dto.command.FinishMatch;
import pl.michal.olszewski.typer.match.dto.command.IntegrateMatchWithLivescore;
import pl.michal.olszewski.typer.match.dto.events.MatchCanceled;
import pl.michal.olszewski.typer.match.dto.events.MatchFinished;

@Component
class MatchUpdater {

  private final MatchFinder matchFinder;
  private final MatchEventPublisher eventPublisher;

  MatchUpdater(MatchFinder matchFinder, MatchEventPublisher eventPublisher) {
    this.matchFinder = matchFinder;
    this.eventPublisher = eventPublisher;
  }

  Match cancelMatch(CancelMatch command) {
    Objects.requireNonNull(command);
    command.validCommand();

    Match match = matchFinder.findOneOrThrow(command.getMatchId());

    MatchCanceled matchCanceled = match.setStatusAsCanceled();
    eventPublisher.sendMatchCanceledToJMS(matchCanceled);
    return match;
  }

  Match finishMatch(FinishMatch command) {
    Objects.requireNonNull(command);
    command.validCommand();

    Match match = matchFinder.findOneOrThrow(command.getMatchId());

    MatchFinished matchFinished = match.setFinalResult(command.getHomeGoals(), command.getAwayGoals());
    eventPublisher.sendMatchFinishedToJMS(matchFinished);
    return match;
  }

  Match integrateMatch(IntegrateMatchWithLivescore command) {
    Objects.requireNonNull(command);
    command.validCommand();

    Match match = matchFinder.findOneOrThrow(command.getMatchId());

    match.integrateMatchWithLivescore(command.getLivescoreId(), command.getLivescoreLeagueId());
    return match;
  }

}
