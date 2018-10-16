package pl.michal.olszewski.typer.match.dto.command;

import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import pl.michal.olszewski.typer.CommandValid;
import pl.michal.olszewski.typer.bet.dto.IllegalGoalArgumentException;
import pl.michal.olszewski.typer.match.dto.MatchNotFoundException;

@AllArgsConstructor
@Getter
@Builder
@ToString
public class FinishMatch implements CommandValid {

  @NotNull
  private Long matchId;
  @NotNull
  private Long homeGoals;
  @NotNull
  private Long awayGoals;

  @Override
  public void validCommand() {
    if (matchId == null) {
      throw new MatchNotFoundException("Nie podano jaki mecz zostal obstawiony");
    }
    if (homeGoals == null || awayGoals == null) {
      throw new IllegalGoalArgumentException("Nieprawidłowa ilość bramek");
    }
  }
}
