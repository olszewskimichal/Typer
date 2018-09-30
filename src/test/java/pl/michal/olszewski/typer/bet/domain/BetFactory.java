package pl.michal.olszewski.typer.bet.domain;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

class BetFactory {

    private BetSaver betSaver;

    BetFactory(BetSaver betSaver) {
        this.betSaver = betSaver;
    }

    void save(Bet bet) {
        betSaver.save(bet);
    }

    void buildBetWithIdAndSave(Long id) {
        IntStream.range(0, 1)
                .mapToObj(number -> Bet.builder().id(id).build())
                .forEach(v -> betSaver.save(v));
    }

    void buildBetWithIdAndStatus(Long id, BetStatus status) {
        IntStream.range(0, 1)
                .mapToObj(number -> Bet.builder().status(status).id(id).build())
                .forEach(v -> betSaver.save(v));
    }

    List<Bet> buildNumberOfBetsForMatchAndSave(int n, Long matchId) {
        return IntStream.range(0, n)
                .mapToObj(number -> Bet.builder().matchId(matchId).build())
                .map(v -> betSaver.save(v))
                .collect(Collectors.toList());
    }

    List<Bet> buildNumberOfBetsForUserAndSave(int n, Long userId) {
        return IntStream.range(0, n)
                .mapToObj(number -> Bet.builder().userId(userId).build())
                .map(v -> betSaver.save(v))
                .collect(Collectors.toList());
    }

    List<Bet> buildNumberOfBetsForMatchRoundAndSave(int n, Long matchRoundId) {
        return IntStream.range(0, n)
                .mapToObj(number -> Bet.builder().matchRoundId(matchRoundId).build())
                .map(v -> betSaver.save(v))
                .collect(Collectors.toList());
    }

    List<Bet> buildNumberOfBetsForMatchAndStatus(int n, Long matchId, BetStatus status) {
        return IntStream.range(0, n)
                .mapToObj(number -> Bet.builder().status(status).matchId(matchId).build())
                .map(v -> betSaver.save(v))
                .collect(Collectors.toList());
    }

    List<Bet> buildNumberOfBetsForUserAndStatus(int n, Long userId, BetStatus status) {
        return IntStream.range(0, n)
                .mapToObj(number -> Bet.builder().status(status).userId(userId).build())
                .map(v -> betSaver.save(v))
                .collect(Collectors.toList());
    }

    List<Bet> buildNumberOfBetsForMatchRoundAndStatus(int n, Long matchRoundId, BetStatus status) {
        return IntStream.range(0, n)
                .mapToObj(number -> Bet.builder().status(status).matchRoundId(matchRoundId).build())
                .map(v -> betSaver.save(v))
                .collect(Collectors.toList());
    }

    void deleteAll() {
        betSaver.deleteAll();
    }
}
