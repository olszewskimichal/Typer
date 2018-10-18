package pl.michal.olszewski.typer.livescore.dto.command;

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
public class FinishLivescoreMatch implements CommandValid {

  private Long livescoreMatchId;
  private Long homeGoals;
  private Long awayGoals;

  @Override
  public void validCommand() {
    if (livescoreMatchId == null) {
      throw new MatchNotFoundException("Nie podano jaki mecz zostal obstawiony");
    }
    if (homeGoals == null || awayGoals == null) {
      throw new IllegalGoalArgumentException("Nieprawidłowa ilość bramek");
    }
  }
}
