package pl.michal.olszewski.typer.bet.domain;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pl.michal.olszewski.typer.bet.dto.read.BetResult;

@Component
/**
 * select  USER_ID,sum(points) from BET group by USER_ID
 * select USER_ID,MATCH_ROUND_ID,sum(points) from BET group by USER_ID,MATCH_ROUND_ID order by USER_ID
 * select USER_ID,sum(points) from BET group by USER_ID order by sum(points) DESC
 * select USER_ID,sum(points) from BET where MATCH_ROUND_ID in ( select id from MATCH_ROUND where LEAGUE_ID=1) group by USER_ID
 */
@Transactional(readOnly = true)
class BetResultService {

  private final BetFinder betFinder;

  BetResultService(BetFinder betFinder) {
    this.betFinder = betFinder;
  }

  List<BetResult> getUserResults(Long userId) {
    return betFinder.findAllBetForUser(userId)
        .stream()
        .map(v -> new BetResult(v.getId(), v.getMatchId(), v.getUserId(), v.getPoints()))
        .collect(Collectors.toList());
  }

  BetResult getResultById(Long betId) {
    Bet bet = betFinder.findOneOrThrow(betId);
    return new BetResult(bet.getId(), bet.getMatchId(), bet.getUserId(), bet.getPoints());
  }

  List<BetResult> getBetResultsForRound(Long roundId) {
    return betFinder.findAllBetForRound(roundId)
        .stream()
        .map(v -> new BetResult(v.getId(), v.getMatchId(), v.getUserId(), v.getPoints()))
        .collect(Collectors.toList());
  }

  List<BetResult> getBetResultsForMatch(Long matchId) {
    return betFinder.findAllBetForMatch(matchId)
        .stream()
        .map(v -> new BetResult(v.getId(), v.getMatchId(), v.getUserId(), v.getPoints()))
        .collect(Collectors.toList());
  }


}
