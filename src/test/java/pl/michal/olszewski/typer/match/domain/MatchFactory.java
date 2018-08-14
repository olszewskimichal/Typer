package pl.michal.olszewski.typer.match.domain;

import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

class MatchFactory {

  Match build(TestEntityManager entityManager) {
    Match match = build(null, null);
    entityManager.persistAndGetId(match);
    entityManager.flush();
    entityManager.clear();
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
        .build();
  }

}
