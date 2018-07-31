package pl.michal.olszewski.typer.bet.dto.command;

import lombok.Builder;
import lombok.Value;
import pl.michal.olszewski.typer.CommandValid;
import pl.michal.olszewski.typer.bet.dto.IllegalGoalArgumentException;
import pl.michal.olszewski.typer.match.dto.MatchNotFoundException;

@Value
@Builder
public class CreateNewBet implements CommandValid {

  private Long betHomeGoals;
  private Long betAwayGoals;
  private Long matchId;

  @Override
  public void validCommand() {
    if (betAwayGoals == null || betHomeGoals == null) {
      throw new IllegalGoalArgumentException("Nieprawidłowa ilość bramek");
    }
    if (matchId == null) {
      throw new MatchNotFoundException("Nie podano jaki mecz zostal obstawiony");
    }
  }
}
