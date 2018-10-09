package pl.michal.olszewski.typer.match.domain;

import java.util.Random;

public class InMemoryMatchLeagueSaver implements MatchLeagueSaver {


  @Override
  public MatchLeague save(MatchLeague matchLeague) {
    return InMemoryMatchLeagueFinder.map.put(matchLeague.getId() != null ? matchLeague.getId() : new Random().nextLong(), matchLeague);
  }

  @Override
  public void deleteAll() {
    InMemoryMatchLeagueFinder.map.clear();
  }

  public void buildMatchLeague(Long leagueId) {
    MatchLeague matchLeague = MatchLeague.builder().id(leagueId).build();
    save(matchLeague);
  }

  public void buildMatchRound(Long leagueId, Long roundId) {
    MatchRound matchRound = MatchRound.builder().id(roundId).matchLeague(InMemoryMatchLeagueFinder.map.get(leagueId)).build();
    InMemoryMatchRoundFinder.map.put(roundId, matchRound);
  }
}


