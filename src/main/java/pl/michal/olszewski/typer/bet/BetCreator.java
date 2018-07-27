package pl.michal.olszewski.typer.bet;

import java.util.Objects;
import pl.michal.olszewski.typer.bet.dto.IllegalGoalArgumentException;
import pl.michal.olszewski.typer.match.dto.MatchNotFoundException;

class BetCreator {

  Bet from(CreateNewBet createNewBet) {
    Objects.requireNonNull(createNewBet);
    if (createNewBet.getBetAwayGoals() == null || createNewBet.getBetHomeGoals() == null) {
      throw new IllegalGoalArgumentException("Nieprawidłowa ilość bramek");
    }
    if (createNewBet.getMatchId() == null) {
      throw new MatchNotFoundException("Nie podano jaki mecz zostal obstawiony");
    }
    return Bet.builder()
        .betAwayGoals(createNewBet.getBetAwayGoals())
        .betHomeGoals(createNewBet.getBetHomeGoals())
        .matchId(createNewBet.getMatchId())
        .build();
  }

}
