package pl.michal.olszewski.typer.match.domain;

import java.time.Instant;

class MatchFactory {

  private MatchSaver saver;

  MatchFactory(MatchSaver matchSaver) {
    saver = matchSaver;
  }


  Match buildAndSave(Long id, MatchStatus status, Long livescoreId) {
    Match match = build(id, status, null, null, livescoreId);
    saver.save(match);
    return match;
  }

  Match buildAndSave(Long id, MatchStatus status) {
    Match match = build(id, status, null, null, null);
    saver.save(match);
    return match;
  }

  Match buildAndSave(Long id, MatchStatus status, Long homeGoals, Long awayGoals) {
    Match match = build(id, status, homeGoals, awayGoals, null);
    match.setMatchRound(new MatchRound());
    saver.save(match);
    return match;
  }

  Match build(Long id, MatchStatus status) {
    return build(id, status, null, null, null);
  }

  Match build(Long id, MatchStatus status, Long homeGoals, Long awayGoals, Long livescoreId) {
    return Match.builder()
        .id(id)
        .matchStatus(status)
        .homeGoals(homeGoals)
        .awayGoals(awayGoals)
        .livescoreId(livescoreId)
        .startDate(Instant.MIN)
        .build();
  }

  public void deleteAll() {
    saver.deleteAll();
  }
}
