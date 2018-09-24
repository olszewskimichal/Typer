package pl.michal.olszewski.typer.bet.dto.command;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import pl.michal.olszewski.typer.CommandValid;
import pl.michal.olszewski.typer.bet.dto.IllegalGoalArgumentException;
import pl.michal.olszewski.typer.match.dto.MatchNotFoundException;
import pl.michal.olszewski.typer.match.dto.MatchRoundNotFoundException;
import pl.michal.olszewski.typer.users.UserNotFoundException;

@AllArgsConstructor
@Getter
@Builder
@ToString
public class CreateNewBet implements CommandValid {

  private Long betHomeGoals;
  private Long betAwayGoals;
  private Long matchId;
  private Long userId;
  private Long matchRoundId;

  @Override
  public void validCommand() {
    if (betAwayGoals == null || betHomeGoals == null) {
      throw new IllegalGoalArgumentException("Nieprawidłowa ilość bramek");
    }
    if (matchId == null) {
      throw new MatchNotFoundException("Nie podano jaki mecz zostal obstawiony");
    }
    if (userId == null) {
      throw new UserNotFoundException("Nie podano jaki uzytkownik obstawił mecz");
    }

    if (matchRoundId == null) {
      throw new MatchRoundNotFoundException("Nie podano z jakiej kolejki pochodzi obstawiany mecz");
    }
  }
}
