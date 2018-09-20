package pl.michal.olszewski.typer.bet.domain;

import java.util.Objects;
import org.springframework.stereotype.Component;
import pl.michal.olszewski.typer.bet.dto.command.CreateNewBet;


@Component
class BetCreator {

  Bet from(CreateNewBet createNewBet) {
    Objects.requireNonNull(createNewBet);
    createNewBet.validCommand();
    return Bet.builder()
        .betAwayGoals(createNewBet.getBetAwayGoals())
        .betHomeGoals(createNewBet.getBetHomeGoals())
        .matchId(createNewBet.getMatchId())
        .userId(createNewBet.getUserId())
        .matchRoundId(createNewBet.getMatchRoundId())
        .build();
  }

}
