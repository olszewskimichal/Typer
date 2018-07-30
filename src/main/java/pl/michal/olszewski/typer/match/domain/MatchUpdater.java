package pl.michal.olszewski.typer.match.domain;

import pl.michal.olszewski.typer.match.dto.command.CancelMatch;

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
    return null;
  }

}
