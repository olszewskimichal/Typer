package pl.michal.olszewski.typer.bet;

import java.util.Objects;

class BetCreator {

  Bet from(CreateNewBet createNewBet) {
    Objects.requireNonNull(createNewBet);
    createNewBet.validCommand();
    return Bet.builder()
        .betAwayGoals(createNewBet.getBetAwayGoals())
        .betHomeGoals(createNewBet.getBetHomeGoals())
        .matchId(createNewBet.getMatchId())
        .build();
  }

}
