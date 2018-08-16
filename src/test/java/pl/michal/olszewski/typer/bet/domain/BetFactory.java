package pl.michal.olszewski.typer.bet.domain;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

class BetFactory {

  private BetFinder betFinder;
  private TestEntityManager entityManager;

  BetFactory(BetFinder betFinder) {
    this.betFinder = betFinder;
  }

  BetFactory(TestEntityManager entityManager) {
    this.entityManager = entityManager;
  }

  void buildNumberOfBetsForMatchAndSave(List<Long> ids, Long matchId) {
    ids.stream()
        .map(id -> Bet.builder().id(id).matchId(matchId).build())
        .forEach(v -> ((InMemoryBetFinder) betFinder).save(v.getId(), v));
  }

  List<Bet> buildNumberOfBetsForMatchAndPersistInDb(int n, Long matchId) {
    List<Bet> list = IntStream.range(0, n)
        .mapToObj(number -> Bet.builder().matchId(matchId).build())
        .map(v -> entityManager.persistAndFlush(v))
        .collect(Collectors.toList());
    entityManager.flush();
    entityManager.clear();
    return list;
  }

}
