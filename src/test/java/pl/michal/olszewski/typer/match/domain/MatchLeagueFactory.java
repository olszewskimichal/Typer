package pl.michal.olszewski.typer.match.domain;

import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

public class MatchLeagueFactory {

  private TestEntityManager entityManager;

  public MatchLeagueFactory(TestEntityManager entityManager) {
    this.entityManager = entityManager;
  }

  public long buildMatchLeague() {
    MatchLeague matchLeague = MatchLeague.builder().build();
    return (Long) entityManager.persistAndGetId(matchLeague);
  }

  public long buildMatchRound(Long leagueId) {
    MatchRound matchRound = MatchRound.builder().matchLeague(entityManager.find(MatchLeague.class, leagueId)).build();
    return (long) entityManager.persistAndGetId(matchRound);
  }


}
