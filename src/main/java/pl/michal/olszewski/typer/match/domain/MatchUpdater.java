package pl.michal.olszewski.typer.match.domain;

import java.util.Objects;
import pl.michal.olszewski.typer.match.dto.command.CancelMatch;
import pl.michal.olszewski.typer.match.dto.command.FinishMatch;

class MatchUpdater {

  /**
   * cancelMatch -> zwraca Match
   * pobieram pierw mecz z bazy o id,
   * aktualizuje mecz ze statusem anulowany
   * wrzucam event o anulowaniu meczu do kolejki
   **/
  private final MatchFinder matchFinder;

  MatchUpdater(MatchFinder matchFinder) {
    this.matchFinder = matchFinder;
  }

  Match cancelMatch(CancelMatch command) {
    Objects.requireNonNull(command);
    Match match = matchFinder.findOneOrThrow(command.getMatchId());
    match.cancelMatch();
    return match;
  }

  /**
   * finishMatch -> zwraca Match
   * pobiera mecz z bazy o id
   * aktualizuje mecz ze statusem zakończony
   * wrzucam event o zakończeniu meczu z wynikiem
   */
  Match finishMatch(FinishMatch command) {
    Objects.requireNonNull(command);
    Match match = matchFinder.findOneOrThrow(command.getMatchId());
    match.finishMatch();
    return match;
  }

}
