package pl.michal.olszewski.typer.bet.domain;

import java.util.Objects;
import pl.michal.olszewski.typer.bet.dto.command.CreateNewBet;


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
