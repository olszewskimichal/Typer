package pl.michal.olszewski.typer.bet.domain;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pl.michal.olszewski.typer.bet.dto.BetNotCheckedException;
import pl.michal.olszewski.typer.bet.dto.read.BetResult;

@Component
@Transactional(readOnly = true)
class BetResultService {

  private final BetFinder betFinder;

  BetResultService(BetFinder betFinder) {
    this.betFinder = betFinder;
  }

  List<BetResult> getUserResults(Long userId) {
    return betFinder.findAllBetForUser(userId)
        .stream()
        .filter(Bet::isChecked)
        .map(v -> new BetResult(v.getId(), v.getMatchId(), v.getUserId(), v.getPoints()))
        .collect(Collectors.toList());
  }

  BetResult getResultByBetId(Long betId) {
    Bet bet = betFinder.findOneOrThrow(betId);
    if (bet.isChecked()) {
      return new BetResult(bet.getId(), bet.getMatchId(), bet.getUserId(), bet.getPoints());
    }
    throw new BetNotCheckedException(String.format("Zaklad o id %s jeszcze nie został sprawdzony", bet.getId()));
  }

  List<BetResult> getBetResultsForRound(Long roundId) {
    return betFinder.findAllBetForRound(roundId)
        .stream()
        .filter(Bet::isChecked)
        .map(v -> new BetResult(v.getId(), v.getMatchId(), v.getUserId(), v.getPoints()))
        .collect(Collectors.toList());
  }

  List<BetResult> getBetResultsForMatch(Long matchId) {
    return betFinder.findAllBetForMatch(matchId)
        .stream()
        .filter(Bet::isChecked)
        .map(v -> new BetResult(v.getId(), v.getMatchId(), v.getUserId(), v.getPoints()))
        .collect(Collectors.toList());
  }


}
