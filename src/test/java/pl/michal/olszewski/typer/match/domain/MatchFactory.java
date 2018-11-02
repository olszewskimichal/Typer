package pl.michal.olszewski.typer.match.domain;

import java.time.Instant;

class MatchFactory {

  private MatchSaver saver;

  MatchFactory(MatchSaver matchSaver) {
    saver = matchSaver;
  }
  
  Match buildAndSave(Long id, MatchStatus status) {
    Match match = build(id, status);
    saver.save(match);
    return match;
  }

  Match buildAndSave(Long id, MatchStatus status, Long homeGoals, Long awayGoals) {
    Match match = build(id, status, homeGoals, awayGoals);
    match.setMatchRound(new MatchRound());
    saver.save(match);
    return match;
  }

  Match build(Long id, MatchStatus status) {
    return build(id, status, null, null);
  }

  Match build(Long id, MatchStatus status, Long homeGoals, Long awayGoals) {
    return Match.builder()
        .id(id)
        .matchStatus(status)
        .homeGoals(homeGoals)
        .awayGoals(awayGoals)
        .startDate(Instant.now())
        .build();
  }

  public void deleteAll() {
    saver.deleteAll();
  }
}
