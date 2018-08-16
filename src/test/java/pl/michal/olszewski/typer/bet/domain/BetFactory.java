package pl.michal.olszewski.typer.bet.domain;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

class BetFactory {

  private BetSaver betSaver;

  BetFactory(BetSaver betSaver) {
    this.betSaver = betSaver;
  }

  void buildBetWithIdAndSave(Long id) {
    IntStream.range(0, 1)
        .mapToObj(number -> Bet.builder().id(id).build())
        .forEach(v -> betSaver.save(v));
  }

  List<Bet> buildNumberOfBetsForMatchAndSave(int n, Long matchId) {
    return IntStream.range(0, n)
        .mapToObj(number -> Bet.builder().matchId(matchId).build())
        .map(v -> betSaver.save(v))
        .collect(Collectors.toList());
  }

  void deleteAll() {
    betSaver.deleteAll();
  }
}
