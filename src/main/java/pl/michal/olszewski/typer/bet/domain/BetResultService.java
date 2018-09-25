package pl.michal.olszewski.typer.bet.domain;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;
import pl.michal.olszewski.typer.bet.dto.read.BetResult;

@Component
/**
 * select  USER_ID,sum(points) from BET group by USER_ID
 * select USER_ID,MATCH_ROUND_ID,sum(points) from BET group by USER_ID,MATCH_ROUND_ID order by USER_ID
 * select USER_ID,sum(points) from BET group by USER_ID order by sum(points) DESC
 * select USER_ID,sum(points) from BET where MATCH_ROUND_ID in ( select id from MATCH_ROUND where LEAGUE_ID=1) group by USER_ID
 */
class BetResultService {

  private final BetFinder betFinder;

  BetResultService(BetFinder betFinder) {
    this.betFinder = betFinder;
  }

  List<BetResult> getUserResults(Long id) {
    return betFinder.findAllBetForUser(id)
        .stream()
        .map(v -> new BetResult(v.getId(), v.getMatchId(), v.getUserId(), v.getPoints(), v.getBetHomeGoals(), v.getBetAwayGoals()))
        .collect(Collectors.toList());
  }


}
