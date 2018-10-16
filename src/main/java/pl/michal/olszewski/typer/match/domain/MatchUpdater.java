package pl.michal.olszewski.typer.match.domain;

import java.util.Objects;
import org.springframework.stereotype.Component;
import pl.michal.olszewski.typer.livescore.dto.command.FinishLivescoreMatch;
import pl.michal.olszewski.typer.match.dto.MatchNotFoundException;
import pl.michal.olszewski.typer.match.dto.command.CancelMatch;
import pl.michal.olszewski.typer.match.dto.command.FinishMatch;
import pl.michal.olszewski.typer.match.dto.command.IntegrateMatchWithLivescore;
import pl.michal.olszewski.typer.match.dto.events.MatchCanceled;
import pl.michal.olszewski.typer.match.dto.events.MatchFinished;

@Component
class MatchUpdater {

  private final MatchFinder matchFinder;
  private final MatchSaver matchSaver;
  private final MatchEventPublisher eventPublisher;

  MatchUpdater(MatchFinder matchFinder, MatchSaver matchSaver, MatchEventPublisher eventPublisher) {
    this.matchFinder = matchFinder;
    this.matchSaver = matchSaver;
    this.eventPublisher = eventPublisher;
  }

  Match cancelMatch(CancelMatch command) {
    Objects.requireNonNull(command);
    command.validCommand();

    Match match = matchFinder.findOneOrThrow(command.getMatchId());

    MatchCanceled matchCanceled = match.setStatusAsCanceled();
    eventPublisher.sendMatchCanceledToJMS(matchCanceled);
    matchSaver.save(match);
    return match;
  }

  Match finishMatch(FinishMatch command) {
    Objects.requireNonNull(command);
    command.validCommand();

    Match match = matchFinder.findOneOrThrow(command.getMatchId());

    MatchFinished matchFinished = match.setFinalResult(command.getHomeGoals(), command.getAwayGoals());
    eventPublisher.sendMatchFinishedToJMS(matchFinished);
    matchSaver.save(match);
    return match;
  }

  Match finishLivescoreMatch(FinishLivescoreMatch finishLivescoreMatch) {
    Objects.requireNonNull(finishLivescoreMatch);
    finishLivescoreMatch.validCommand();

    Match match = matchFinder.findByLivescoreId(finishLivescoreMatch.getLivescoreMatchId())
        .orElseThrow(() -> new MatchNotFoundException(String.format("Mecz o livescoreId %s nie istnieje", finishLivescoreMatch.getLivescoreMatchId())));
    match.setFinalResult(finishLivescoreMatch.getHomeGoals(), finishLivescoreMatch.getAwayGoals());
    return matchSaver.save(match);
  }

  Match integrateMatch(IntegrateMatchWithLivescore command) {
    Objects.requireNonNull(command);
    command.validCommand();

    Match match = matchFinder.findOneOrThrow(command.getMatchId());

    match.integrateMatchWithLivescore(command.getLivescoreId(), command.getLivescoreLeagueId());
    matchSaver.save(match);
    return match;
  }

}
