package pl.michal.olszewski.typer.match.domain;

import java.util.Objects;
import pl.michal.olszewski.typer.match.dto.command.CancelMatch;
import pl.michal.olszewski.typer.match.dto.command.FinishMatch;

class MatchUpdater {

  private final MatchFinder matchFinder;

  MatchUpdater(MatchFinder matchFinder) {
    this.matchFinder = matchFinder;
  }

  /**
   * setStatusAsCanceled -> zwraca Match
   * pobieram pierw mecz z bazy o id,
   * aktualizuje mecz ze statusem anulowany
   * wrzucam event o anulowaniu meczu do kolejki
   **/
  Match cancelMatch(CancelMatch command) {
    Objects.requireNonNull(command);
    command.validCommand();
    Match match = matchFinder.findOneOrThrow(command.getMatchId());
    match.setStatusAsCanceled();
    return match;
  }

  /**
   * setStatusAsFinished -> zwraca Match
   * pobiera mecz z bazy o id
   * aktualizuje mecz ze statusem zakończony
   * wrzucam event o zakończeniu meczu z wynikiem
   */
  Match finishMatch(FinishMatch command) {
    Objects.requireNonNull(command);
    command.validCommand();
    Match match = matchFinder.findOneOrThrow(command.getMatchId());
    match.setStatusAsFinished();
    return match;
  }

}
